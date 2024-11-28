package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.StatisticDTO;
import org.dainn.charitybe.dtos.request.StatisticRequest;

public interface IStatisticService {
    StatisticDTO findCampaignStatistic(StatisticRequest request);
    byte[] generateStatisticExcel(StatisticRequest request);
}
