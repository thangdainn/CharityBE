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
public class FinancialReportDTO extends BaseDTO {

    @NotNull(message = "Total receive is required")
    private BigDecimal totalReceived;

    @NotNull(message = "Total remain is required")
    private BigDecimal totalRemain;

    @NotNull(message = "Project Id is required")
    private Integer campaignId;

    private String campaignName;

    private Integer recipientId;

    private String recipientName;
}
