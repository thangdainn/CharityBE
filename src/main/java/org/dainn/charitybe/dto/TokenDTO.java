package org.dainn.charitybe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private Integer id;
    private String refreshToken;
    private String refreshTokenExpirationDate;
    private Integer userId;
}
