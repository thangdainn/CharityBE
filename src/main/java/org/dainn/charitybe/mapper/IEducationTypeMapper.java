package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dto.EducationTypeDTO;
import org.dainn.charitybe.entity.EducationTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IEducationTypeMapper {
    EducationTypeEntity toEntity(EducationTypeDTO dto);
    
    EducationTypeDTO toDTO(EducationTypeEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    EducationTypeEntity updateEntity(@MappingTarget EducationTypeEntity entity, EducationTypeDTO dto);
}
