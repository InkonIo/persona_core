package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SubscriptionStatusRequestDto(
        @NotNull(message = "Идентификатор профиля не указан")
        @Schema(description = "Идентификатор профиля по которому нужно проверить подписку")
        Long userId) {
}
