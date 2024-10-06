package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.RoleDTO;
import org.dainn.charitybe.models.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    RoleEntity toEntity(RoleDTO dto);

    RoleDTO toDTO(RoleEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    RoleEntity updateEntity(@MappingTarget RoleEntity entity, RoleDTO request);
}
