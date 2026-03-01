package kz.persona.core.mapper;

import kz.persona.core.model.entity.Notification;
import kz.persona.core.model.response.NotificationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface NotificationMapper {

    NotificationResponseDto map(Notification notification);

    List<NotificationResponseDto> map(List<Notification> notifications);

}
