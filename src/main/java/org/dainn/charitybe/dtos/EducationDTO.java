package org.dainn.charitybe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDTO extends BaseDTO{
    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    @NotNull(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Address is required")
    @NotNull(message = "Address is required")
    private String address;

    private Integer status = 1;

    @NotNull(message = "Education type is required")
    @JsonProperty("type_id")
    private Integer typeId;
}
