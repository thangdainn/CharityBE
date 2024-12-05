package org.dainn.charitybe.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpDTO extends BaseDTO {
    private String code;
    private String email;
    private Boolean isUsed = false;
}
