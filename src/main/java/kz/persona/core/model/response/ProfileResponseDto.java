package kz.persona.core.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.persona.core.model.response.dictionaries.DictDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;
    @Schema(description = "Полное имя", example = "Тест тестов тестович")
    private String fullName;
    @Schema(description = "Фотография пользователя", example = "https://persona.com.s3.amazonaws.com/987119ea-c4f3-478f-a716-0ef403d531b5")
    private String imageUrl;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Дата рождения в строгом формате", example = "14-11-2000", type = "string")
    private LocalDate dateOfBirth;
    @Schema(description = "Идентификатор города", example = "1")
    private DictDto city;
    @Schema(description = "Идентификатор страны", example = "1")
    private DictDto region;
    @Schema(description = "Идентификатор страны", example = "1")
    private DictDto country;
    @Schema(description = "Идентификатор статуса", example = "1")
    private DictDto status;
    @Schema(description = "Флаг Persona", example = "true")
    private Boolean isMentor;
    @Schema(description = "Флаг наставничество", example = "true")
    private Boolean hasSubscription;
    @Schema(description = "Рейтинг в системе, считается среднее значение. SUM/TOTAL_COUNT", example = "9")
    private Integer rating;
    @Schema(description = "Количество отзывов", example = "25")
    private Integer ratingCount;
    @Schema(description = "Возраст пользователя", example = "25")
    private Integer age;
    @Schema(description = "Количество дней в проекте", example = "300")
    private Integer days;
}
