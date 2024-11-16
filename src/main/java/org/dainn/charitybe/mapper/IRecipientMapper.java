package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.RecipientDTO;
import org.dainn.charitybe.models.RecipientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IRecipientMapper {
    RecipientEntity toEntity(RecipientDTO dto);

    RecipientDTO toDTO(RecipientEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    RecipientEntity updateEntity(@MappingTarget RecipientEntity entity, RecipientDTO dto);
}
