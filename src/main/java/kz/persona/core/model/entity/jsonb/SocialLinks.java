package kz.persona.core.model.entity.jsonb;

import io.swagger.v3.oas.annotations.media.Schema;

public record SocialLinks(
        @Schema(description = "Ссылка на LinkedIn", example = "https://www.linkedin.com/in/id/")
        String linkedIn,
        @Schema(description = "Ссылка на Telegram", example = "https://t.me/id")
        String telegram) {
}
