package kz.persona.core.mapper;

import kz.persona.core.model.entity.mapped.DictModel;
import kz.persona.core.model.response.dictionaries.DictDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DictionaryMapper {

    DictDto map(DictModel model);

    List<DictDto> map(List<? extends DictModel> models);

}
