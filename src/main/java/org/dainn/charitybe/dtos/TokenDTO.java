package org.dainn.charitybe.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private Integer id;
    private String refreshToken;
    private Date refreshTokenExpirationDate;
    private Integer userId;
}
