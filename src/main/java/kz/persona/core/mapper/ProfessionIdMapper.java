package kz.persona.core.mapper;

import kz.persona.core.model.entity.dictionaries.Profession;
import kz.persona.core.model.entity.mapped.BaseModel;
import kz.persona.core.repository.ProfessionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class ProfessionIdMapper {

    @Autowired
    private ProfessionRepository repository;

    public Set<Profession> mapByIds(List<Long> professions) {
        if (professions == null) return null;
        return new HashSet<>(repository.findAllById(professions));
    }

    public Set<Long> mapToIds(Set<Profession> professions) {
        return Optional.ofNullable(professions)
                .orElseGet(HashSet::new)
                .stream()
                .map(BaseModel::getId)
                .collect(Collectors.toSet());
    }
}
