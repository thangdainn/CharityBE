package org.dainn.charitybe.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationDTO extends BaseDTO {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Name is required")
    private String name;

    private Boolean isPaid = false;

    private Boolean isAnonymous = false;

    @NotNull(message = "Campaign is required")
    private Integer campaignId;

    private Integer userId;
}
