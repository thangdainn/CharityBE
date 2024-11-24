package org.dainn.charitybe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {
    private long totalDonations;
    private Integer totalCampaigns;
    private List<StatisticItemDTO> data = new ArrayList<>();
}
