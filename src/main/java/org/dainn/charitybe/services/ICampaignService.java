package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.CampaignDTO;
import org.dainn.charitybe.dtos.CampaignDetailDTO;
import org.dainn.charitybe.dtos.request.CampaignSearch;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ICampaignService {
    CampaignDTO insert(CampaignDTO dto);
    CampaignDTO update(CampaignDTO dto);
    CampaignDTO updateCurrentAmount(BigDecimal amount, Integer id);
    Page<CampaignDTO> findByUserId(Integer userId, CampaignSearch request);
    CampaignDTO findById(Integer id);
    CampaignDetailDTO findByCode(String code);
    List<CampaignDTO> findAll();
    Page<CampaignDTO> findAllByFilters(CampaignSearch request);
}
