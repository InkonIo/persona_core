package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SubscribeRequestDto(
        @NotNull(message = "Идентификатор пользователя не указан")
        @Schema(description = "Идентификатор пользователя к которому подписываются/отписываются")
        Long toUser) {
}
