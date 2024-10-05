package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dto.UserDTO;
import org.dainn.charitybe.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserEntity toEntity(UserDTO request);
//    UserRequest toUserRequest(RegisterRequest request);
//    UserRequest toUserRequest(UserDTO dto);

//    UserDTO toDTO(UserRequest userRequest);
    @Mapping(target = "roleName", source = "role.name")
    UserDTO toDTO(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    UserEntity updateEntity(@MappingTarget UserEntity entity, UserDTO request);
}
