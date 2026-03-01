package kz.persona.core.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserJwtInfoResponseDto(
        @Schema(description = "Логин пользователя", example = "a.temir")
        String username,
        @Schema(description = "Почта пользователя", example = "a.temir@digitaizer.kz")
        String email,
        @Schema(description = "Полное имя пользователя", example = "Амир Темир")
        String fullName) {
}
