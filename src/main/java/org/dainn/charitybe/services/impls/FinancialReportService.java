package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.dtos.request.FinancialReportSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.models.FinancialReportEntity;
import org.dainn.charitybe.repositories.IFinancialReportRepository;
import org.dainn.charitybe.services.IFinancialReportService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.dainn.charitybe.mapper.IFinancialReportMapper;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialReportService implements IFinancialReportService {
    private final IFinancialReportRepository financialReportRepository;
    private final IFinancialReportMapper financialReportMapper;

    @Override
    public FinancialReportDTO insert(FinancialReportDTO dto) {
        FinancialReportEntity entity = financialReportMapper.toEntity(dto);
        return financialReportMapper.toDTO(financialReportRepository.save(entity));
    }

    @Override
    public FinancialReportDTO update(FinancialReportDTO dto) {
        FinancialReportEntity old = financialReportRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.FINANCIAL_REPORT_NOT_EXISTED));
        FinancialReportEntity entity = financialReportMapper.updateEntity(old, dto);
        return financialReportMapper.toDTO(financialReportRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        financialReportRepository.deleteByIdCustom(id);
    }

    @Override
    public FinancialReportDTO findById(Integer id) {
        return financialReportRepository.findById(id)
                .map(financialReportMapper::toDTO)
                .orElseThrow(() -> new AppException(ErrorCode.FINANCIAL_REPORT_NOT_EXISTED));
    }

    @Override
    public FinancialReportDTO findByProjectId(Integer projectId) {
        return financialReportRepository.findByProjectId(projectId)
                .map(financialReportMapper::toDTO)
                .orElseThrow(() -> new AppException(ErrorCode.FINANCIAL_REPORT_NOT_EXISTED));
    }

    @Override
    public FinancialReportDTO findByStudentId(Integer studentId) {
        return null;
    }

    @Override
    public List<FinancialReportDTO> findAll(Integer status) {
        return financialReportRepository.findAll().stream()
                .map(financialReportMapper::toDTO).toList();
    }

    @Override
    public Page<FinancialReportDTO> findAllByConditions(FinancialReportSearch request) {
        Page<FinancialReportEntity> page = financialReportRepository.findAllByConditions(request.getProjectId(), request.getStudentId(), Paging.getPageable(request));
        return page.map(financialReportMapper::toDTO);
    }
}
