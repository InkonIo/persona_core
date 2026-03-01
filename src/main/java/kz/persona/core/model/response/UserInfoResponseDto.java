package kz.persona.core.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.persona.core.model.entity.jsonb.SalaryBound;
import kz.persona.core.model.response.dictionaries.DictDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UserInfoResponseDto(
        @Schema(description = "Идентификатор пользователя", example = "1")
        Long id,
        @Schema(description = "Логин пользователя", example = "abylayamirov")
        String login,
        @Schema(description = "Полное имя", example = "Тест тестов тестович")
        String fullName,
        @JsonFormat(pattern = "dd-MM-yyyy")
        @Schema(description = "Дата рождения в строгом формате", example = "14-11-2000", type = "string")
        LocalDate dateOfBirth,
        @Schema(description = "Информация о городе")
        DictDto city,
        @Schema(description = "Информация о регионе", example = "1")
        DictDto region,
        @Schema(description = "Информация о стране", example = "1")
        DictDto country,
        @Schema(description = "Информация сферы работы", example = "1")
        DictDto fieldOfWork,
        @Schema(description = "Информация об профессиях")
        List<DictDto> professions,
        @Schema(description = "Информация статуса", example = "1")
        DictDto status,
        @Schema(description = "Почтовый адрес пользователя", example = "test@test.com")
        String email,
        @Schema(description = "Ссылки на социальные сети")
        String linksToSocial,
        @Schema(description = "Информация семейного статуса", example = "1")
        DictDto maritalStatus,
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
        @Schema(description = "Фотография пользователя", example = "https://persona.com.s3.amazonaws.com/987119ea-c4f3-478f-a716-0ef403d531b5")
        String imageUrl,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @Schema(description = "Дата регистрации", example = "22.02.2024 15:50:45")
        LocalDateTime createdAt,
        @Schema(description = "Флаг видимости профиля", example = "true")
        Boolean visible,
        @Schema(description = "Флаг наставничество", example = "true")
        Boolean isMentor,
        @Schema(description = "Возраст пользователя", example = "25")
        Integer age) {
}
