package kz.persona.core.mapper;

import kz.persona.core.model.entity.Rating;
import kz.persona.core.model.entity.User;
import kz.persona.core.model.entity.jsonb.SalaryBound;
import kz.persona.core.model.entity.mongo.ProfileDocument;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProfessionIdMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ProfileDocumentMapper {

    @Mapping(target = "hasSubscription", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "internalId", source = "id")
    @Mapping(target = "salaryFrom", expression = "java(mapSalaryFrom(user))")
    @Mapping(target = "salaryTo", expression = "java(mapSalaryTo(user))")
    @Mapping(target = "region", source = "user.city.region")
    @Mapping(target = "country", source = "user.city.region.country")
    ProfileDocument map(User user);

    default Long mapSalaryFrom(User user) {
        return Optional.ofNullable(user.getSalary())
                .map(SalaryBound::lower)
                .orElse(null);
    }

    default Long mapSalaryTo(User user) {
        return Optional.ofNullable(user.getSalary())
                .map(SalaryBound::upper)
                .orElse(null);
    }

    @AfterMapping
    default void afterMapping(User source, @MappingTarget ProfileDocument target) {
        if (source == null) {
            return;
        }
        List<Rating> ratings = source.getRatings();
        int size = Optional.ofNullable(ratings).map(List::size).orElse(0);
        double rating = Optional.ofNullable(ratings)
                .orElseGet(ArrayList::new)
                .stream()
                .mapToInt(Rating::getValue)
                .average()
                .orElse(0);
        target.setRating(BigDecimal.valueOf(rating)
                .multiply(BigDecimal.valueOf(10))
                .setScale(2, RoundingMode.HALF_UP)
        );
        target.setRatingCount(size);
    }

}
