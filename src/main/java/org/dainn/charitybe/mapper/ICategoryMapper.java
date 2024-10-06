package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.CategoryDTO;
import org.dainn.charitybe.models.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {
    CategoryEntity toEntity(CategoryDTO dto);
    CategoryDTO toDTO(CategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    CategoryEntity updateEntity(@MappingTarget CategoryEntity entity, CategoryDTO dto);
}
