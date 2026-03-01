package kz.persona.core.mapper;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.entity.dictionaries.Status;
import kz.persona.core.repository.StatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class StatusIdMapper {

    @Autowired
    private StatusRepository repository;

    public Status mapById(Long status) {
        return repository.findById(status)
                .orElseThrow(() -> new PersonaCoreException("Статус по идентификатору \"%s\" не найден".formatted(status)));
    }

    public Long mapId(Status status) {
        return status.getId();
    }

}
