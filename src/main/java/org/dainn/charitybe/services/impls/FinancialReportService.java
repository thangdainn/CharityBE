package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.dtos.request.FinancialReportSearch;
import org.dainn.charitybe.models.FinancialReportEntity;
import org.dainn.charitybe.repositories.IFinancialReportRepository;
import org.dainn.charitybe.services.IFinancialReportService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.dainn.charitybe.mapper.IFinancialReportMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialReportService implements IFinancialReportService {
    private final IFinancialReportRepository financialReportRepository;
    private final IFinancialReportMapper financialReportMapper;

    @Override
    public FinancialReportDTO insert(FinancialReportDTO dto) {
        return null;
    }

    @Override
    public FinancialReportDTO update(FinancialReportDTO dto) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public FinancialReportDTO findById(Integer id) {
        return null;
    }

    @Override
    public FinancialReportDTO findByProjectId(Integer projectId) {
        return null;
    }

    @Override
    public FinancialReportDTO findByStudentId(Integer studentId) {
        return null;
    }

    @Override
    public List<FinancialReportDTO> findAll(Integer status) {
        return financialReportRepository.findAllByStatus(status).stream()
                .map(financialReportMapper::toDTO).toList();
    }

    @Override
    public Page<FinancialReportDTO> findAllByConditions(FinancialReportSearch request) {
        Page<FinancialReportEntity> page = financialReportRepository.findAllByConditions(request.getProjectId(), request.getStudentId(), request.getStatus(), Paging.getPageable(request));
        return page.map(financialReportMapper::toDTO);
    }
}
