package org.dainn.charitybe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity{
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = 1;
        }
    }

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "category")
    private List<CampaignEntity> projects = new ArrayList<>();

}
