package org.dainn.charitybe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticItemDTO {
    private CampaignDTO campaigns;
    private String owner;
    private Integer totalDonations;
}
