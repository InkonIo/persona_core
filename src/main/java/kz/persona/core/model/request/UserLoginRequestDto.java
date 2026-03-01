package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(
        @NotBlank(message = "Логин не должен быть пустым")
        @Schema(description = "Логин пользователя", example = "login")
        String login,
        @NotBlank(message = "Пароль не должен быть пустым")
        @Schema(description = "Пароль пользователя", example = "password")
        String password) {
}
