package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.dtos.request.FinancialReportSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.mapper.IFinancialReportMapper;
import org.dainn.charitybe.models.FinancialReportEntity;
import org.dainn.charitybe.repositories.ICampaignRepository;
import org.dainn.charitybe.repositories.IFinancialReportRepository;
import org.dainn.charitybe.repositories.IRecipientRepository;
import org.dainn.charitybe.services.IFinancialReportService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialReportService implements IFinancialReportService {
    private final IFinancialReportRepository financialReportRepository;
    private final IFinancialReportMapper financialReportMapper;
    private final ICampaignRepository campaignRepository;
    private final IRecipientRepository recipientRepository;

    @Transactional
    @Override
    public FinancialReportDTO insert(FinancialReportDTO dto) {
        FinancialReportEntity entity = financialReportMapper.toEntity(dto);
        setAttributes(entity, dto);
        return financialReportMapper.toDTO(financialReportRepository.save(entity));
    }

    @Transactional
    @Override
    public FinancialReportDTO update(FinancialReportDTO dto) {
        FinancialReportEntity old = financialReportRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.FINANCIAL_REPORT_NOT_EXISTED));
        FinancialReportEntity entity = financialReportMapper.updateEntity(old, dto);
        setAttributes(entity, dto);
        return financialReportMapper.toDTO(financialReportRepository.save(entity));
    }

    private void setAttributes(FinancialReportEntity entity, FinancialReportDTO dto) {
        entity.setCampaign(campaignRepository.findById(dto.getCampaignId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_EXISTED)));
        entity.setRecipient(recipientRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new AppException(ErrorCode.RECIPIENT_NOT_EXISTED)));
    }

    @Override
    public void delete(List<Integer> ids) {
        financialReportRepository.deleteAllByIdInBatchCustom(ids);
    }

    @Override
    public FinancialReportDTO findById(Integer id) {
        return financialReportRepository.findById(id)
                .map(financialReportMapper::toDTO)
                .orElseThrow(() -> new AppException(ErrorCode.FINANCIAL_REPORT_NOT_EXISTED));
    }


    @Override
    public List<FinancialReportDTO> findByRecipientId(Integer recipientId) {
        return financialReportRepository.findByRecipientId(recipientId).stream()
                .map(financialReportMapper::toDTO).toList();
    }

    @Override
    public List<FinancialReportDTO> findByCampaignId(Integer campaignId) {
        return financialReportRepository.findByCampaignId(campaignId).stream()
                .map(financialReportMapper::toDTO).toList();
    }

    @Override
    public List<FinancialReportDTO> findAll() {
        return financialReportRepository.findAll().stream()
                .map(financialReportMapper::toDTO).toList();
    }


    @Override
    public Page<FinancialReportDTO> findAllByConditions(FinancialReportSearch request) {
        Page<FinancialReportEntity> page = financialReportRepository.findAllByConditions(request.getCampaignId() ,request.getRecipientId(), Paging.getPageable(request));
        return page.map(financialReportMapper::toDTO);
    }
}
