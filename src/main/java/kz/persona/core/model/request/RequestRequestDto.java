package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record RequestRequestDto(
        @Schema(description = "Текст запроса")
        String message,
        @Schema(description = "Заголовок запроса")
        String title
) {
}
