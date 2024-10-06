package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.SponsorDTO;
import org.dainn.charitybe.models.SponsorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ISponsorMapper {
    SponsorEntity toEntity(SponsorDTO dto);
    SponsorDTO toDTO(SponsorEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    SponsorEntity updateEntity(@MappingTarget SponsorEntity entity, SponsorDTO dto);
}
