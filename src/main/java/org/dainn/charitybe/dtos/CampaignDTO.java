package org.dainn.charitybe.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.enums.CampaignFor;
import org.dainn.charitybe.enums.CampaignStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO extends BaseDTO {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;

    private String code;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Target amount is required")
    private BigDecimal targetAmount;

    @NotNull(message = "Current amount is required")
    private BigDecimal currentAmount;

    @NotNull(message = "Thumbnail is required")
    @NotBlank(message = "Thumbnail is required")
    private String thumbnail;

    @NotNull(message = "Start date is required")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "End date is required")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotNull(message = "Campaign for ?")
    private CampaignFor campaignFor;

    @NotNull(message = "Education is required")
    private Integer educationId;

    private CampaignStatus status;

    @NotNull(message = "Category is required")
    private Integer categoryId;

    @NotNull(message = "Created by is required")
    private Integer createdId;
}
