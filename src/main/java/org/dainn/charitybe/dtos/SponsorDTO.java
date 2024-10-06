package org.dainn.charitybe.dtos;

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
public class SponsorDTO extends BaseDTO{
    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    @NotNull(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
    @NotNull(message = "Phone is required")
    private String phone;
}
