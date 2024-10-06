package org.dainn.charitybe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sponsors")
public class SponsorEntity extends BaseEntity {

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "email", nullable = false)
        private String email;

        @Column(name = "phone", nullable = false)
        private String phone;

        @Column(name = "address", nullable = false)
        private String address;

        @Column(name = "logo", nullable = false)
        private String logo;

        @OneToMany(mappedBy = "sponsor")
        private List<DonationSponsorEntity> donationSponsors = new ArrayList<>();
}
