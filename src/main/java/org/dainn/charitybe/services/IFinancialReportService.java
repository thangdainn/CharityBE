package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.dtos.request.FinancialReportSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFinancialReportService {
    FinancialReportDTO insert(FinancialReportDTO dto);
    FinancialReportDTO update(FinancialReportDTO dto);
    void delete(Integer id);
    FinancialReportDTO findById(Integer id);
    FinancialReportDTO findByProjectId(Integer projectId);
    FinancialReportDTO findByStudentId(Integer studentId);
    List<FinancialReportDTO> findAll(Integer status);
    Page<FinancialReportDTO> findAllByConditions(FinancialReportSearch request);
}
