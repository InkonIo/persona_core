package kz.persona.core.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record NotificationRequestDto(
        @NotBlank(message = "Заголовок уведомления не должно быть пустым")
        @Schema(description = "Заголовок уведомления", example = "Новое сообщение")
        String title,
        @NotBlank(message = "Текст уведомления не должно быть пустым")
        @Schema(description = "Текст уведомления", example = "Вам написали сообщение")
        String body,
        @NotEmpty(message = "Токены не должны быть пустыми")
        @Schema(description = "Токены пользователей")
        List<String> to,
        @Schema(description = "Дополнительные параметры для уведомлений")
        Map<String, Object> data) {
}
