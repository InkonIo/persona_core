package kz.persona.core.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record MessageDto(
        @Schema(description = "Сообщение от рест запроса", example = "Разные текста от ошибок и информационных")
        String message) {

}
