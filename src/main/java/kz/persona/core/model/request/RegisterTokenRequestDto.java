package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegisterTokenRequestDto(
        @NotBlank(message = "Expo Go Token не должно быть пустым")
        @Schema(description = "Expo Go Token", example = "ExponentPushToken[**********************]")
        String token) {
}
