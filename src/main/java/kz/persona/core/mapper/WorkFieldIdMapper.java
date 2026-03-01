package kz.persona.core.mapper;

import kz.persona.core.exception.PersonaCoreException;
import kz.persona.core.model.entity.dictionaries.WorkField;
import kz.persona.core.repository.WorkFieldRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class WorkFieldIdMapper {

    @Autowired
    private WorkFieldRepository repository;

    public WorkField mapById(Long fieldOfWork) {
        return repository.findById(fieldOfWork)
                .orElseThrow(() -> new PersonaCoreException("Сфера работы по идентификатору \"%s\" не найден".formatted(fieldOfWork)));
    }

    public Long mapId(WorkField workField) {
        return workField.getId();
    }

}
