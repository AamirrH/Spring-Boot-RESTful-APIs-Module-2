package com.codeexample.springbootrestapi.controllers;


import com.codeexample.springbootrestapi.dto.EmployeeDTO;
import com.codeexample.springbootrestapi.entities.EmployeeEntity;
import com.codeexample.springbootrestapi.repositories.EmployeeRepository;
import com.codeexample.springbootrestapi.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController//Provides a JSON response, Jackson Library converts to JSON
public class EmployeeController {

    private final EmployeeService employeeService;

    // Injecting employeeService Bean
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GetMapping - used to get data/resource from the server.
    @GetMapping(path="/employees/{employeeID}")
    // Value inside employeeID gets injected inside ID.
    public EmployeeDTO getEmployee(@PathVariable(name = "employeeID") int ID){
        // A Method from EmployeeService is called, which further calls a JPARepo Method through a Repository Bean
        return employeeService.findById(ID);
    }

    //Request Params are optional, and we may or may not provide them in the URL.
    @GetMapping(path = "/allemployees")
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) int age,
                                 @RequestParam(required = false) String name){
        return employeeService.findAll() ;

    }

    //Browser can only send GET Requests, so we need Postman for these mappings.
    @PostMapping(path = "/employees")
    // @Valid - Checks the EmployeeDTO for valid annotations.
    public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        return employeeService.save(inputEmployee);
    }

    //Changing whole data of an employee on the backend, use PUT Mapping
    @PutMapping(path = "/employee/{employeeID}")
    public EmployeeDTO updateEmployeebyId(@RequestBody EmployeeDTO inputEmployee, @PathVariable(name="employeeID") int empID){

        return employeeService.updateEmployeeID(inputEmployee,empID);
    }

    @DeleteMapping(path = "/{employeeID}")
    public String deleteEmployee(@PathVariable(name = "employeeID") int empID){
        employeeService.deleteEmployee(empID);
        return "Employee Deleted Successfully";
    }

    // Partially Update details of an employee
    @PatchMapping(path = "/{employeeId}" )
    public EmployeeDTO updateEmployeeDetails(@PathVariable(name = "employeeId") int empID, Map<String,Object> fieldUpdate){
        return employeeService.updateEmployeeDetails(empID,fieldUpdate);

    }






}
