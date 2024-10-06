package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.TokenDTO;
import org.dainn.charitybe.models.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITokenMapper {
    TokenEntity toEntity(TokenDTO dto);

    @Mapping(target = "userId", source = "user.id")
    TokenDTO toDTO(TokenEntity entity);
}
