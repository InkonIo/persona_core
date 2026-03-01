package kz.persona.core.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginResponseDto(
        @Schema(description = "Токен авторизации", example = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldU...")
        String accessToken,
        @Schema(description = "Токен обновления", example = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldU...")
        String refreshToken) {
}
