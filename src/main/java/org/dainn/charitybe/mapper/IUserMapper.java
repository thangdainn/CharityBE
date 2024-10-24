package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.UserDTO;
import org.dainn.charitybe.dtos.auth.UserRegister;
import org.dainn.charitybe.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserEntity toEntity(UserDTO request);
    UserDTO toDTO(UserRegister register);

//    UserDTO toDTO(UserRequest userRequest);
    @Mapping(target = "roleName", source = "role.name")
    UserDTO toDTO(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    UserEntity updateEntity(@MappingTarget UserEntity entity, UserDTO request);
}
