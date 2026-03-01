package kz.persona.core.model.entity.jsonb;

import io.swagger.v3.oas.annotations.media.Schema;

public record SalaryBound(
        @Schema(description = "Высокая желаемая зарплата", example = "1000000000")
        Long upper,
        @Schema(description = "Минимальная желаемая зарплата", example = "1000000")
        Long lower) {
}
