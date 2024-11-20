package org.dainn.charitybe.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FinancialReportSearch extends BaseSearch{
    private Integer campaignId;
    private Integer recipientId;
    private Integer status = 1;
}
