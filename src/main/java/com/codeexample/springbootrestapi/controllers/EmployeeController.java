package com.codeexample.springbootrestapi.controllers;


import com.codeexample.springbootrestapi.dto.EmployeeDTO;
import com.codeexample.springbootrestapi.entities.EmployeeEntity;
import com.codeexample.springbootrestapi.repositories.EmployeeRepository;
import com.codeexample.springbootrestapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO inputEmployee){
        return employeeService.save(inputEmployee);
    }

    @PutMapping
    public String updateEmployeebyId(){
        return "Hello From POST";
    }





}
