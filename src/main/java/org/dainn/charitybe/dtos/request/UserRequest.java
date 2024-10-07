package org.dainn.charitybe.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.dainn.charitybe.enums.Provider;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private Integer id;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    private Provider provider = Provider.LOCAL;

    private String password;

    @JsonProperty("role_name")
    private String roleName;

    private Integer status = 1;
}
