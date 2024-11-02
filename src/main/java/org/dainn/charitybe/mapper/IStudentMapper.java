package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.StudentDTO;
import org.dainn.charitybe.models.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IStudentMapper {
    StudentEntity toEntity(StudentDTO dto);

    StudentDTO toDTO(StudentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    StudentEntity updateEntity(@MappingTarget StudentEntity entity, StudentDTO dto);
}
