package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.OtpDTO;
import org.dainn.charitybe.models.OtpEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IOtpMapper {
    OtpEntity toEntity(OtpDTO dto);
    OtpDTO toDTO(OtpEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    OtpEntity updateEntity(@MappingTarget OtpEntity entity, OtpDTO request);
}
