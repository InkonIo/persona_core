package kz.persona.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.persona.core.exception.AuthenticationError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errorCode;
        String errorDescription = Optional.ofNullable(authException)
                .map(Throwable::getMessage)
                .orElse("Token error: %s".formatted(authException));
        switch (authException) {
            case InvalidBearerTokenException ignored -> {
                errorCode = "invalid_token";
                if (errorDescription.toLowerCase().contains("expired")) {
                    errorCode = "jwt_expired";
                }
            }
            case InsufficientAuthenticationException ignored -> errorCode = "token_required";
            case null, default -> errorCode = "token_error";
        }
        AuthenticationError authenticationError = new AuthenticationError(errorCode, errorDescription);
        if (log.isDebugEnabled()) {
            log.debug("Authentication error: {}", authenticationError);
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(authenticationError));
    }
}
