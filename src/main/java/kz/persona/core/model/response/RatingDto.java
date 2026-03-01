package kz.persona.core.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.persona.core.model.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    public RatingDto(Rating rating) {
        this.value = rating.getValue();
        this.createdDate = rating.getCreatedAt().toLocalDate();
    }

    @Schema(description = "Рейтинг выставленный пользователем", example = "5")
    private Short value;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Schema(description = "Дата выставления рейтинга", example = "14.11.2000", type = "string")
    LocalDate createdDate;
}

