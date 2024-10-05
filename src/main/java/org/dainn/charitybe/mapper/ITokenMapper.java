package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dto.TokenDTO;
import org.dainn.charitybe.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITokenMapper {
    TokenEntity toEntity(TokenDTO dto);

    @Mapping(target = "userId", source = "user.id")
    TokenDTO toDTO(TokenEntity entity);
}
