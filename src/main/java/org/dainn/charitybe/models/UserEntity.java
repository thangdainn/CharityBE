package org.dainn.charitybe.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.enums.Provider;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "status", columnDefinition = "DEFAULT 1")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CharityProjectEntity> charityProjects = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DonationEntity> donations = new ArrayList<>();
}
