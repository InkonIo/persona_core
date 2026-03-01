package kz.persona.core.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
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
        return Keycloak.getInstance(
                properties.getServerUrl(),
                properties.getRealm(),
                properties.getUsername(),
                properties.getPassword(),
                properties.getClientId(),
                properties.getClientSecret()
        );
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
