package kz.persona.core.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SubscriptionStatusResponseDto(
        @Schema(description = "Флаг показывающий подписан ли текущий пользователь на текущий профиль")
        Boolean subscribed) {
}
