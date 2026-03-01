package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NotificationSeenRequestDto(
        @NotNull(message = "Идентификатор пользователя не указан")
        @Schema(description = "Идентификатор пользователя")
        Long userId,
        @Schema(description = "Идентификаторы уведомлений для изменение статуса")
        List<Long> notificationIds) {
}
