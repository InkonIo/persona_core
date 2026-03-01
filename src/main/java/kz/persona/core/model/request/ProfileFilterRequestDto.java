package kz.persona.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileFilterRequestDto {
    @Schema(description = "Фильтр для поиска по идентификатору пользователя", example = "1")
    private Long id;
    @Schema(description = "Фильтр для поиска по ФИО", example = "Абылай")
    private String fullName;
    @Schema(description = "Фильтр для поиска по статусу", example = "1")
    private Long statusId;
    @Schema(description = "Фильтр для поиска по городу", example = "1")
    private Long cityId;
    @Schema(description = "Фильтр для поиска по стране", example = "1")
    private Long countryId;
    @Schema(description = "Фильтр для поиска по сфере работы", example = "1")
    private Long workFieldId;
    @Schema(description = "Фильтр для поиска по семейному положению", example = "1")
    private Long maritalStatusId;
    @Schema(description = "Фильтр для поиска по профессиям", example = "[1, 2]")
    private Set<Long> professionsIds;
    @Schema(description = "Фильтр для поиска по возрасту начиная от определенного года", example = "18")
    private Integer ageFrom;
    @Schema(description = "Фильтр для поиска по возрасту заканчивая до определенного года", example = "40")
    private Integer ageTo;
    @Schema(description = "Фильтр для поиска по образованию и курсам", example = "МУИТ")
    private String education;
    @Schema(description = "Фильтр для поиска по опыту работы и навыку", example = "Вязания")
    private String skills;
    @Schema(description = "Фильтр для поиска по зарплатной плате, начиная от", example = "10000")
    private Long salaryFrom;
    @Schema(description = "Фильтр для поиска по зарплатной плате, заканчивая до", example = "150000")
    private Long salaryTo;
    @Schema(description = "Фильтр для поиска по работе мечты", example = "Предприниматель")
    private String dreamWork;
    @Schema(description = "Фильтр для поиска по хобби", example = "футбол")
    private String hobby;
}
