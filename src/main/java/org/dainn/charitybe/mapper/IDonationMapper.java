package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dto.DonationDTO;
import org.dainn.charitybe.entity.DonationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IDonationMapper {
    DonationEntity toEntity(DonationDTO dto);

    @Mapping(target = "projectId", source = "charityProject.id")
    @Mapping(target = "userId", source = "user.id")
    DonationDTO toDTO(DonationEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    DonationEntity updateEntity(@MappingTarget DonationEntity entity, DonationDTO dto);
}
