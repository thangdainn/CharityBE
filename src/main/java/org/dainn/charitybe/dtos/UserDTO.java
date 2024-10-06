package org.dainn.charitybe.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.enums.Provider;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {
    private String email;
    private String name;
    private String avatar;

    @JsonIgnore
    private String password;

    private Provider provider;
    private String roleName;
}
