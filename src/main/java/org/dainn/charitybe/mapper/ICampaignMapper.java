package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.CampaignDTO;
import org.dainn.charitybe.dtos.CampaignDetailDTO;
import org.dainn.charitybe.models.CampaignEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ICampaignMapper {
    CampaignEntity toEntity(CampaignDTO dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "createdId", source = "user.id")
    @Mapping(target = "educationId", source = "education.id")
    CampaignDTO toDTO(CampaignEntity entity);

    @Mapping(target = "owner", source = "user")
    CampaignDetailDTO toDetailDTO(CampaignEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    CampaignEntity updateEntity(@MappingTarget CampaignEntity entity, CampaignDTO dto);
}
