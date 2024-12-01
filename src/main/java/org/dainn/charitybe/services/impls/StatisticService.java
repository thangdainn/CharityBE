package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.StatisticDTO;
import org.dainn.charitybe.dtos.StatisticItemDTO;
import org.dainn.charitybe.dtos.request.StatisticRequest;
import org.dainn.charitybe.mapper.ICampaignMapper;
import org.dainn.charitybe.models.CampaignEntity;
import org.dainn.charitybe.repositories.ICampaignRepository;
import org.dainn.charitybe.repositories.IDonationRepository;
import org.dainn.charitybe.repositories.specification.SearchOperation;
import org.dainn.charitybe.repositories.specification.SpecSearchCriteria;
import org.dainn.charitybe.repositories.specification.SpecificationBuilder;
import org.dainn.charitybe.services.IStatisticService;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final ICampaignRepository campaignRepository;
    private final IDonationRepository donationRepository;
    private final ICampaignMapper campaignMapper;
    private final ExcelService excelService;
    @Override
    public StatisticDTO findCampaignStatistic(StatisticRequest request) {
        SpecificationBuilder<CampaignEntity> builder = new SpecificationBuilder<>();
        List<CampaignEntity> campaigns;
        Specification<CampaignEntity> spec;

        builder.with("status", SearchOperation.EQUALITY, request.getStatus(), false);
        builder.with("startDate", SearchOperation.GREATER_THAN_OR_EQUAL, request.getStartDate(), false);
        builder.with("startDate", SearchOperation.LESS_THAN_OR_EQUAL, request.getEndDate(), false);
        spec = builder.build();
        if (request.getCategoryId() != null && !request.getCategoryId().equals(0)) {
            List<SpecSearchCriteria> prjCriteria = new ArrayList<>();
            prjCriteria.add(new SpecSearchCriteria("id", SearchOperation.EQUALITY, request.getCategoryId(), true));
            Specification<CampaignEntity> prjSpec = builder.joinTableWithCondition("category", prjCriteria);
            spec = Specification.where(spec).and(prjSpec);
        }
        campaigns = campaignRepository.findAll(Objects.requireNonNull(spec), Paging.getSort("startDate", "asc"));
//        List<CampaignDTO> campaignDTOs = campaigns.stream().map(campaignMapper::toDTO).toList();

        StatisticDTO statisticDTO = new StatisticDTO();
        long totalDonations = 0;
        statisticDTO.setTotalCampaigns(campaigns.size());
        for (CampaignEntity campaign : campaigns) {
            StatisticItemDTO item = new StatisticItemDTO();
            item.setOwner(campaign.getUser().getName());
            item.setTotalDonations(donationRepository.countByCampaignIdAndIsPaid(campaign.getId(), true));
            item.setCampaigns(campaignMapper.toDTO(campaign));
            totalDonations += item.getTotalDonations();
            statisticDTO.getData().add(item);
        }
        statisticDTO.setTotalDonations(totalDonations);
        return statisticDTO;
    }

    @Override
    public byte[] generateStatisticExcel(StatisticRequest request) {
        StatisticDTO data = findCampaignStatistic(request);
        return excelService.generateStatisticExcel(data, request.getStartDate(), request.getEndDate());
    }
}
