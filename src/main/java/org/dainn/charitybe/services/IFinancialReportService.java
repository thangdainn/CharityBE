package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.dtos.request.FinancialReportSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFinancialReportService {
    FinancialReportDTO insert(FinancialReportDTO dto);
    FinancialReportDTO update(FinancialReportDTO dto);
    void delete(List<Integer> ids);
    FinancialReportDTO findById(Integer id);
    List<FinancialReportDTO> findByRecipientId(Integer recipientId);
    List<FinancialReportDTO> findByCampaignId(Integer campaignId);
    List<FinancialReportDTO> findAll();
    Page<FinancialReportDTO> findAllByConditions(FinancialReportSearch request);
}
