package kz.persona.core.mapper;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.entity.dictionaries.MaritalStatus;
import kz.persona.core.repository.MaritalStatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class MaritalStatusIdMapper {

    @Autowired
    private MaritalStatusRepository repository;

    public MaritalStatus mapById(Long maritalStatus) {
        return repository.findById(maritalStatus)
                .orElseThrow(() -> new PersonaCoreException("Семейное положение по идентификатору \"%s\" не найден".formatted(maritalStatus)));
    }

    public Long mapId(MaritalStatus maritalStatus) {
        return maritalStatus.getId();
    }


}
