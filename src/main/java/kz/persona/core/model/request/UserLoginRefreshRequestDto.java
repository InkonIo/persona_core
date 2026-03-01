package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRefreshRequestDto(
        @NotBlank(message = "Refresh token не должно быть пустым")
        @Schema(description = "Refresh token для обновления токена")
        String refreshToken) {
}
