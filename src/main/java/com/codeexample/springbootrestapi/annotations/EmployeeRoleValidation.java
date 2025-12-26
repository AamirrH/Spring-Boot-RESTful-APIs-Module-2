package com.codeexample.springbootrestapi.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = {EmployeeRoleValidator.class})
public @interface EmployeeRoleValidation {

    // Message to display if validation fails and isValid return false.
    String message() default "Employee Role Validation Failed";


    Class<?>[] groups() default{};

    // Payload is basically the data, which has to validated
    Class<? extends Payload>[] payload() default {};
}
