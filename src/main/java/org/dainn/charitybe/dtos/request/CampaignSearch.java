package org.dainn.charitybe.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.dainn.charitybe.enums.CampaignFor;
import org.dainn.charitybe.enums.CampaignStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class CampaignSearch extends BaseSearch {
    private CampaignFor campaignFor;
    private Integer categoryId;
    private CampaignStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
