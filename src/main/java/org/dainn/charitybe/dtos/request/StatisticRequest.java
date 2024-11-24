package org.dainn.charitybe.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.dainn.charitybe.enums.CampaignStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class StatisticRequest {
    private Integer categoryId;
    private CampaignStatus status = CampaignStatus.COMPLETED;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate = new Date();

    public StatisticRequest() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        this.startDate = calendar.getTime();
    }
}
