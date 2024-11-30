package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.DonationDTO;
import org.dainn.charitybe.models.DonationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IDonationMapper {
    DonationEntity toEntity(DonationDTO dto);

    @Mapping(target = "campaignId", source = "campaign.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "campaignCode", source = "campaign.code")
    @Mapping(target = "campaignName", source = "campaign.name")
    DonationDTO toDTO(DonationEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    DonationEntity updateEntity(@MappingTarget DonationEntity entity, DonationDTO dto);
}
