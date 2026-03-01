package kz.persona.core.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.persona.core.model.response.dictionaries.DictDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProfileFullResponseDto extends ProfileResponseDto {

    @Schema(description = "Почтовый адрес", example = "mail@gmail.com")
    private String email;

    @Schema(description = "Идентификатор сферы деятельности", example = "1")
    private DictDto workField;

    @Schema(description = "Идентификаторы профессии")
    private List<DictDto> professions;

    @Schema(description = "Идентификаторы семейного статуса")
    private DictDto maritalStatus;

    @Schema(description = "Образование и курсы")
    private String education;

    @Schema(description = "Опыт работы и навыки")
    private String skills;

    @Schema(description = "Желаемый доход начиная от")
    private Long salaryFrom;

    @Schema(description = "Желаемый доход заканчивая до")
    private Long salaryTo;

    @Schema(description = "Работа мечты")
    private String dreamWork;

    @Schema(description = "Хобби")
    private String hobby;

    @Schema(description = "Ссылки на социальные страницы")
    private String socialLinks;
}
