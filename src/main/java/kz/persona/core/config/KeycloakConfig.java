package kz.persona.core.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder; // Добавь этот импорт
import org.keycloak.authorization.client.AuthzClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final KeycloakConfigProperties properties;

    @Bean
    public Keycloak keycloak() {
        // Используем Builder вместо getInstance для гибкой настройки
        return KeycloakBuilder.builder()
                .serverUrl(properties.getServerUrl())
                .realm(properties.getRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS) // Переключаем на работу через Service Account
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .build();
    }

    @Bean
    public AuthzClient authzClient() {
        return AuthzClient.create(new org.keycloak.authorization.client.Configuration(
                properties.getServerUrl(),
                properties.getRealm(),
                properties.getClientId(),
                Map.of("secret", properties.getClientSecret()),
                null
        ));
    }
}
