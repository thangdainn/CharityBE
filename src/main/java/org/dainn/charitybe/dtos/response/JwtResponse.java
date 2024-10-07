package org.dainn.charitybe.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token_type = "Bearer";
    private String access_token;

    public JwtResponse(String access_token) {
        this.access_token = access_token;
    }
}
