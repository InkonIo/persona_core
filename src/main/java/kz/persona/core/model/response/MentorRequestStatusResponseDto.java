package kz.persona.core.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.persona.core.model.enumaration.MentorRequestStatus;

public record MentorRequestStatusResponseDto(
        @Schema(description = "Статус заявки на наставничество")
        MentorRequestStatus status) {
}
