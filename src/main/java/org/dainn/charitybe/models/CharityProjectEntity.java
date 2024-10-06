package org.dainn.charitybe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.enums.ProjectFor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "charity_projects")
public class CharityProjectEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

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

    @Column(name = "project_for", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectFor projectFor;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "charityProject")
    private List<DonationSponsorEntity> donationSponsors = new ArrayList<>();

    @OneToMany(mappedBy = "charityProject")
    private List<DonationEntity> donations = new ArrayList<>();

}
