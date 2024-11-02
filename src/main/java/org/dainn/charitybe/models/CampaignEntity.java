package org.dainn.charitybe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.enums.CampaignFor;
import org.dainn.charitybe.enums.CampaignStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "campaigns")
public class CampaignEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "target_amount", nullable = false)
    @Digits(integer = 20, fraction = 0)
    private BigDecimal targetAmount;

    @Column(name = "current_amount", nullable = false)
    @Digits(integer = 20, fraction = 0)
    private BigDecimal currentAmount;

    @Column(name = "campaign_for", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignFor campaignFor;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_id")
    private EducationEntity education;

    @OneToMany(mappedBy = "campaign")
    private List<DonationEntity> donations = new ArrayList<>();

    @OneToMany(mappedBy = "campaign")
    private List<FinancialReportEntity> financialReports = new ArrayList<>();

}
