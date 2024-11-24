package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.CampaignDTO;
import org.dainn.charitybe.dtos.CampaignDetailDTO;
import org.dainn.charitybe.dtos.request.CampaignSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICampaignService {
    CampaignDTO insert(CampaignDTO dto);
    CampaignDTO update(CampaignDTO dto);
    CampaignDTO findById(Integer id);
    CampaignDetailDTO findByCode(String code);
    List<CampaignDTO> findAll();
    Page<CampaignDTO> findAllByFilters(CampaignSearch request);
}
