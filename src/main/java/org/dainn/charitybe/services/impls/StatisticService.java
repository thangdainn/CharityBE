package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.CampaignDTO;
import org.dainn.charitybe.dtos.StatisticDTO;
import org.dainn.charitybe.dtos.request.StatisticRequest;
import org.dainn.charitybe.enums.CampaignStatus;
import org.dainn.charitybe.mapper.ICampaignMapper;
import org.dainn.charitybe.repositories.ICampaignRepository;
import org.dainn.charitybe.services.IStatisticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final ICampaignRepository campaignRepository;
    private final ICampaignMapper campaignMapper;
    @Override
    public StatisticDTO findCampaignStatistic(StatisticRequest request) {
        List<CampaignDTO> campaigns = campaignRepository.findAllByStatusAndEndDateBetween(CampaignStatus.COMPLETED, request.getStart(), request.getEnd())
                .stream().map(campaignMapper::toDTO).toList();
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setQuantity(campaigns.size());
        statisticDTO.setData(campaigns);
        return statisticDTO;
    }
}
