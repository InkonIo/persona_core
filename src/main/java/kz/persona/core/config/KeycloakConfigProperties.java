package kz.persona.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfigProperties {

    private String serverUrl;
    private String realm;
    private String username;
    private String password;
    private String clientId;
    private String clientSecret;

}
