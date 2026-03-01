package kz.persona.core.mapper;

import kz.persona.core.model.entity.User;
import kz.persona.core.model.request.UserUpsertRequestDto;
import kz.persona.core.model.response.UserInfoResponseDto;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                CityIdMapper.class,
                MaritalStatusIdMapper.class,
                ProfessionIdMapper.class,
                StatusIdMapper.class,
                WorkFieldIdMapper.class,
                DictionaryMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "keycloakId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "visible", ignore = true)
    @Mapping(target = "isMentor", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "workField", source = "fieldOfWork")
    @Mapping(target = "education", source = "educationAndCourses")
    @Mapping(target = "skills", source = "experienceAndSkills")
    @Mapping(target = "socialLinks", source = "linksToSocial")
    User map(UserUpsertRequestDto requestDto);

    @Mapping(source = "workField", target = "fieldOfWork")
    @Mapping(source = "education", target = "educationAndCourses")
    @Mapping(source = "skills", target = "experienceAndSkills")
    @Mapping(source = "socialLinks", target = "linksToSocial")
    @Mapping(target = "age", expression = "java(mapAge(user))")
    @Mapping(source = "user.city.region.country", target = "country")
    @Mapping(source = "user.city.region", target = "region")
    UserInfoResponseDto map(User user);

    default Integer mapAge(User user) {
        return Optional.ofNullable(user)
                .map(User::getDateOfBirth)
                .map(dob -> Period.between(dob, LocalDate.now()))
                .map(Period::getYears)
                .orElse(null);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "keycloakId", ignore = true)
    @Mapping(target = "visible", ignore = true)
    @Mapping(target = "isMentor", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "workField", source = "fieldOfWork")
    @Mapping(target = "education", source = "educationAndCourses")
    @Mapping(target = "skills", source = "experienceAndSkills")
    @Mapping(target = "socialLinks", source = "linksToSocial")
    void update(UserUpsertRequestDto requestDto, @MappingTarget User user);

}
