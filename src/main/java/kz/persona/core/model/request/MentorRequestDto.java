package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MentorRequestDto(
        @NotNull(message = "Идентификатор пользователя не указан")
        @Schema(description = "Идентификатор пользователя к которому отправляют запрос на менторство")
        Long userId) {
}
