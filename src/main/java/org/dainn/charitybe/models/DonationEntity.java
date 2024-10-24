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
@Table(name = "donations")
public class DonationEntity extends BaseEntity {

    @Column(name = "amount", nullable = false)
    @Digits(integer = 20, fraction = 0)
    private BigDecimal amount;

    @Column(name = "is_paid", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPaid;

    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private CharityProjectEntity charityProject;
}
