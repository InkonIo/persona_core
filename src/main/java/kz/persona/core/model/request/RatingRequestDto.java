package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RatingRequestDto(
        @NotNull(message = "Идентификатор пользователя не указан")
        @Schema(description = "Идентификатор пользователя к которому подписываются/отписываются")
        Long toUser,
        @Min(value = 1, message = "Минимум рейтинг - 1")
        @Max(value = 10, message = "Максимум рейтинг - 10")
        @NotNull(message = "Значение рейтинга не должно быть пустым")
        Integer value) {
}
