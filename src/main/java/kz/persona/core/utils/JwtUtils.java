package kz.persona.core.utils;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.response.UserJwtInfoResponseDto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

@Slf4j
@UtilityClass
public class JwtUtils {

    public UserJwtInfoResponseDto getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken token) {
            if (log.isDebugEnabled()) {
                log.debug("Attributes: {}", token.getTokenAttributes());
            }
            Map<String, Object> tokenAttributes = token.getTokenAttributes();
            String fullName = (String) tokenAttributes.get("given_name");
            String username = (String) tokenAttributes.get("preferred_username");
            String email = (String) tokenAttributes.get("email");
            return new UserJwtInfoResponseDto(username, email, fullName);
        }
        throw new PersonaCoreException("Отсутствует токен");
    }

}
