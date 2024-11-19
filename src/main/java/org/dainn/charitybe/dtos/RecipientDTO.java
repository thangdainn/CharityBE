package org.dainn.charitybe.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipientDTO extends BaseDTO {

    @NotBlank(message = "Code is required")
    @NotNull(message = "Code is required")
    private String code;

    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    @NotNull(message = "Phone is required")
    private String phone;
}
