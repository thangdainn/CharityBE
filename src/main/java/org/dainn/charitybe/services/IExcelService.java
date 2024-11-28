package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.DonationDTO;
import org.dainn.charitybe.dtos.StatisticDTO;
import org.dainn.charitybe.models.DonationEntity;

import java.util.Date;
import java.util.List;

public interface IExcelService {
    byte[] generateStatisticExcel(StatisticDTO data, Date fromDate, Date toDate);
    byte[] generateDonationExcel(List<DonationDTO> data, String campaignName);
}
