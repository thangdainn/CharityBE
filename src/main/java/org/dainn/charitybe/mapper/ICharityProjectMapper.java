package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dto.CharityProjectDTO;
import org.dainn.charitybe.entity.CharityProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ICharityProjectMapper {
    CharityProjectEntity toEntity(CharityProjectDTO dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "createdId", source = "user.id")
    CharityProjectDTO toDTO(CharityProjectEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    CharityProjectEntity updateEntity(@MappingTarget CharityProjectEntity entity, CharityProjectDTO dto);
}
