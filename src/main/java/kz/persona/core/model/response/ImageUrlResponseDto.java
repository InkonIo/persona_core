package kz.persona.core.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ImageUrlResponseDto(
        @Schema(description = "Ссылка на фотографию", example = "https://osv-s3.s3.amazonaws.com/987119ea-c4f3-478f-a716-0ef403d531b5")
        String imageUrl) {
}
