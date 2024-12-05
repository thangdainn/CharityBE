package org.dainn.charitybe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otps")
public class OtpEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String code;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "expiredDate", nullable = false)
    private Date expiredDate;

    @Column(name = "isUsed", nullable = false)
    private Boolean isUsed;
}
