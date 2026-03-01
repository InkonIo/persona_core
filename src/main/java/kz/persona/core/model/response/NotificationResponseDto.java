package kz.persona.core.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record NotificationResponseDto(
        @Schema(description = "Идентификатор уведомления", example = "1")
        Long id,
        @Schema(description = "Текст уведомления", example = "Запрос на дружбу")
        String title,
        @Schema(description = "Описание уведомления", example = "Описание")
        String description,
        @Schema(description = "Прочитано или нет", example = "true - активная и не прочитана, false - уже прочитана")
        Boolean isActive,
        @JsonFormat(pattern = "dd.MM.yyyy hh:mm:SS")
        @Schema(description = "Дата уведомления", example = "14.11.2000 14:45:35", type = "string")
        LocalDateTime createdAt) {
}
