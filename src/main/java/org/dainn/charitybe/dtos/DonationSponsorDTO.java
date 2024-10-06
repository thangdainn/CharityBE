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
public class DonationSponsorDTO extends BaseDTO {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Paid is required")
    private Integer isPaid;

    @NotNull(message = "Project Id is required")
    private Integer projectId;

    @NotNull(message = "Sponsor Id is required")
    private Integer sponsorId;
}
