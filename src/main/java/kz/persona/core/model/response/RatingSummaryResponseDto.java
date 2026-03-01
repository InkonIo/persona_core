package kz.persona.core.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingSummaryResponseDto {

    @Schema(description = "Рейтинг в системе, считается среднее значение", example = "9")
    private BigDecimal totalRating;

    @Schema(description = "Количество отзывов", example = "25")
    private Integer totalRatingCount;

    @Schema(description = "Отзывы")
    private List<RatingDto> ratings;

}
