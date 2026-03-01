package kz.persona.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import kz.persona.core.config.KeycloakConfigProperties;
import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.request.UserLoginRefreshRequestDto;
import kz.persona.core.model.request.UserUpsertRequestDto;
import kz.persona.core.model.response.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final AuthzClient authzClient;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final KeycloakConfigProperties keycloakConfigProperties;

    private String clientRealm;

    @PostConstruct
    private void postInit() {
        clientRealm = authzClient.getConfiguration().getRealm();
    }

    public String createKeycloakUser(UserUpsertRequestDto requestDto) {
        RealmResource realm = keycloak.realm(clientRealm);
        UsersResource users = realm.users();
        List<UserRepresentation> userRepresentations = users.searchByUsername(requestDto.login(), Boolean.TRUE);
        if (CollectionUtils.isNotEmpty(userRepresentations)) {
            return userRepresentations.get(0).getId();
        }
        UserRepresentation userRepresentation = this.getUserRepresentation(requestDto);
        try (var response = users.create(userRepresentation)) {
            if (response.getStatus() / 100 != 2) {
                log.error("Error response while create user in keycloak with login: {}", requestDto.login());
                throw new PersonaCoreException("Ошибка при создании пользователя");
            }
            String path = response.getLocation().getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception ex) {
            log.error("Error while creating keycloak user, login: {}", requestDto.login(), ex);
            throw new PersonaCoreException("Ошибка при создании пользователя");
        }
    }

    private UserRepresentation getUserRepresentation(UserUpsertRequestDto requestDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(requestDto.login());
        userRepresentation.setFirstName(requestDto.fullName());
        userRepresentation.setEmail(requestDto.email());
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(Boolean.FALSE);
        credentialRepresentation.setValue(requestDto.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        userRepresentation.setEnabled(Boolean.TRUE);
        return userRepresentation;
    }

    public UserLoginResponseDto login(String login, String password) {
        try {
            AccessTokenResponse accessTokenResponse =
                    authzClient.obtainAccessToken(login, password);
            return new UserLoginResponseDto(
                    accessTokenResponse.getToken(),
                    accessTokenResponse.getRefreshToken()
            );
        } catch (Exception ex) {
            log.error("Error while authorizing user with login: {}", login, ex);
            throw new PersonaCoreException("Ошибка при авторизации пользователя");
        }
    }

    public UserLoginResponseDto refresh(UserLoginRefreshRequestDto requestDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            parameters.add("grant_type", "refresh_token");
            parameters.add("client_id", keycloakConfigProperties.getClientId());
            parameters.add("client_secret", keycloakConfigProperties.getClientSecret());
            parameters.add("refresh_token", requestDto.refreshToken());

            log.info("Client_id: {}, client_secret: {}",
                    keycloakConfigProperties.getClientId(), keycloakConfigProperties.getClientSecret());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);

            String tokenEndpoint = authzClient.getServerConfiguration().getTokenEndpoint();
            log.info("Token endpoint: {}", tokenEndpoint);
            AccessTokenResponse accessTokenResponse = Optional.of(restTemplate.exchange(
                            tokenEndpoint,
                            HttpMethod.POST,
                            entity,
                            AccessTokenResponse.class))
                    .filter(resp -> resp.getStatusCode().is2xxSuccessful())
                    .map(HttpEntity::getBody)
                    .orElseThrow(() -> new PersonaCoreException("Error while refreshing token"));
            return new UserLoginResponseDto(
                    accessTokenResponse.getToken(),
                    accessTokenResponse.getRefreshToken()
            );
        } catch (Exception ex) {
            log.error("Error while refreshing token", ex);
            throw new PersonaCoreException("Ошибка при обновлении токена");
        }
    }

    public void deleteUser(String keycloakId) {
        RealmResource realm = keycloak.realm(clientRealm);
        UsersResource users = realm.users();
        try (Response response = users.delete(keycloakId)) {
            if (Boolean.FALSE.equals(response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL))) {
                throw new PersonaCoreException("Удаление дало неуспешный ответ, response: %s".formatted(objectMapper.writeValueAsString(response)));
            }
        } catch (Exception ex) {
            log.error("Error while deleting user from keycloak, id: {}", keycloakId, ex);
        }
    }

}
