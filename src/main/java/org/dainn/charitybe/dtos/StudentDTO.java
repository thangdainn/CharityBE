package org.dainn.charitybe.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO extends BaseDTO {

    @NotBlank(message = "MSSV is required")
    @NotNull(message = "MSSV is required")
    private String mssv;

    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone is required")
    @NotNull(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Address is required")
    @NotNull(message = "Address is required")
    private String address;

    private Integer status = 1;
}
