package com.codeexample.springbootrestapi.dto;


import com.codeexample.springbootrestapi.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;
    @NotNull(message = "Name is Required")
    @NotEmpty(message = "Name of the Employee cannot be empty")
    @Size(min = 3,max = 15,message = "Name should be greater than 2 characters and lesser than 16 characters")
    private String name;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be a valid email")
    // xyz@mail.com format
    private String email;

    //@NotBlank(message = "Age should be provided ")
    @Max(value = 100,message = "Age cannot be greater than 100")
    @Min(value = 18,message = "Age cannot be less than 18")
    private Integer age;

    @EmployeeRoleValidation
    private String role;

    @Positive(message = "Salary cannot be negative") @NotNull()
    @Digits(integer = 6,fraction = 4,message = "Salary should be in the form of XXXXXX.YYYY")
    @DecimalMin(value = "156000.0000")
    @DecimalMax(value = "200000.0000")
    private Double salary;

    @PastOrPresent(message = "Date of Joining should be in the past or present")
    private LocalDate dateOfJoining;
    @AssertTrue(message = "Employee should be active")
    private Boolean active;



}
