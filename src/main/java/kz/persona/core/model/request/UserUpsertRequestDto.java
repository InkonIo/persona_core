package kz.persona.core.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kz.persona.core.model.entity.jsonb.SalaryBound;

import java.time.LocalDate;
import java.util.List;

public record UserUpsertRequestDto(
        @NotBlank(message = "Логин не должен быть пустым")
        @Schema(description = "Логин пользователя", example = "abylayamirov")
        String login,
        @NotBlank(message = "Полное имя не должно быть пустым")
        @Schema(description = "Полное имя", example = "Тест тестов тестович")
        String fullName,
        @NotNull(message = "Дата рождение не должно быть пустым")
        @JsonFormat(pattern = "dd-MM-yyyy")
        @Schema(description = "Дата рождения в строгом формате", example = "14-11-2000", type = "string")
        LocalDate dateOfBirth,
        @NotBlank(message = "Пароль не должен быть пустым")
        @Schema(description = "Пароль пользователя", example = "pass")
        String password,
        @NotNull(message = "Город не указан")
        @Schema(description = "Идентификатор города", example = "1")
        Long city,
        @NotNull(message = "Сфера работы не указана")
        @Schema(description = "Идентификатор сферы работы", example = "1")
        Long fieldOfWork,
        @NotEmpty(message = "Хотя бы одна профессия должна быть указана")
        @Schema(description = "Идентификаторы профессии")
        List<Long> professions,
        @NotBlank(message = "Почтовый адрес не должен быть пустым")
        @Email(message = "Почтовый адрес не соответствует формату")
        @Schema(description = "Почтовый адрес пользователя", example = "test@test.com")
        String email,
        @Schema(description = "Ссылки на социальные сети")
        String linksToSocial,
        @Schema(description = "Идентификатор семейного статуса", example = "1")
        Long maritalStatus,
        @Schema(description = "Образование и курсы", example = "Текст описание в произвольном виде")
        String educationAndCourses,
        @Schema(description = "Опыт работы и навыки", example = "Текст описание в произвольном виде")
        String experienceAndSkills,
        @Schema(description = "Желаемый уровень дохода")
        SalaryBound salary,
        @Schema(description = "Работа мечты", example = "Сварщик")
        String dreamWork,
        @Schema(description = "Хобби", example = "Летать")
        String hobby,
        @Schema(description = "Фотография пользователя", example = "https://s3.amazonaws.com/persona.com/987119ea-c4f3-478f-a716-0ef403d531b5")
        String imageUrl) {
}
