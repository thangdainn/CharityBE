package org.dainn.charitybe.dto;

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

    @NotBlank(message = "Name is required")
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Day of birth is required")
    private Date dob;

    @NotBlank(message = "Gender is required")
    @NotNull(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Address is required")
    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Education is required")
    private Integer educationId;
}
