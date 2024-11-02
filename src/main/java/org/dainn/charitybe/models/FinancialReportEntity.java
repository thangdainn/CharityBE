package org.dainn.charitybe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "financial_reports")
public class FinancialReportEntity extends BaseEntity {

    @Column(name = "total_received", nullable = false)
    @Digits(integer = 20, fraction = 0)
    private BigDecimal totalReceived;

    @Column(name = "total_remain", nullable = false)
    @Digits(integer = 20, fraction = 0)
    private BigDecimal totalRemain;

    @JoinColumn(name = "campaign_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CampaignEntity campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

}
