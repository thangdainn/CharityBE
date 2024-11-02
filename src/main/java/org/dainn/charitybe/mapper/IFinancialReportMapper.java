package org.dainn.charitybe.mapper;

import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.models.FinancialReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IFinancialReportMapper {
    FinancialReportEntity toEntity(FinancialReportDTO dto);

    @Mapping(target = "campaignId", source = "campaign.id")
    @Mapping(target = "studentId", source = "student.id")
    FinancialReportDTO toDTO(FinancialReportEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    FinancialReportEntity updateEntity(@MappingTarget FinancialReportEntity entity, FinancialReportDTO dto);
}
