package org.dainn.charitybe.models;

import jakarta.persistence.*;
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
@Table(name = "educations")
public class EducationEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = 1;
        }
    }
    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "education")
    private List<CampaignEntity> campaigns = new ArrayList<>();

}
