package org.dainn.charitybe.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.dainn.charitybe.enums.Provider;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private Integer id;
    private Integer status = 1;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;
    private Provider provider = Provider.LOCAL;
    private String password;
    private String roleName;
}
