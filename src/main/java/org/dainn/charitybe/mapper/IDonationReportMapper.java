package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dto.DonationSponsorDTO;
import org.dainn.charitybe.entity.DonationSponsorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IDonationReportMapper {
    DonationSponsorEntity toEntity(DonationSponsorDTO dto);

    @Mapping(target = "projectId", source = "charityProject.id")
    @Mapping(target = "sponsorId", source = "sponsor.id")
    DonationSponsorDTO toDTO(DonationSponsorEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    DonationSponsorEntity updateEntity(@MappingTarget DonationSponsorEntity entity, DonationSponsorDTO dto);
}
