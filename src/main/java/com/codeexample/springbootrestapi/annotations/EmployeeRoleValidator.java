package com.codeexample.springbootrestapi.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation, String> {

    /*Constraint Validator is an Interface which takes Custom-Annotation-Interface
     and the Target datatype as input and has certain methods like isValid
    which provide writing custom validation logic using the Target datatype as input
    and returns a boolean value.
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<String> roles = List.of("USER","ADMIN","MANAGER","DEVELOPER");
        if(roles.contains(s) == true){
            return true;
        }
        else{
            return false;
        }
    }
}
