package org.dainn.charitybe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.enums.CampaignStatus;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDetailDTO extends BaseDTO {
    private String name;
    private String code;
    private String description;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private String thumbnail;
    private Date startDate;
    private Date endDate;
    private String accountNumber;
    private String bankName;
    private EducationDTO education;
    private CampaignStatus status;
    private CategoryDTO category;
    private UserDTO owner;
    private Integer totalDonation = 0;
}
