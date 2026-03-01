package kz.persona.core.mapper;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.entity.dictionaries.City;
import kz.persona.core.repository.CityRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class CityIdMapper {

    @Autowired
    private CityRepository repository;

    public City mapById(Long city) {
        return repository.findById(city)
                .orElseThrow(() -> new PersonaCoreException("Город по идентификатору \"%s\" не найден".formatted(city)));
    }

    public Long mapId(City city) {
        return city.getId();
    }

}
