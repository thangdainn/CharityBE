package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.EducationDTO;
import org.dainn.charitybe.models.EducationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IEducationMapper {
    EducationEntity toEntity(EducationDTO dto);

    @Mapping(target = "typeId", source = "educationType.id")
    EducationDTO toDTO(EducationEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    EducationEntity updateEntity(@MappingTarget EducationEntity entity, EducationDTO dto);
}
