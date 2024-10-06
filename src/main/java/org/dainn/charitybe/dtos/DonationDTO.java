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

    @NotNull(message = "Total remain is required")
    private BigDecimal totalRemain;

    @NotNull(message = "Project Id is required")
    private Integer projectId;

    @NotNull(message = "User Id Id is required")
    private Integer userId;
}
