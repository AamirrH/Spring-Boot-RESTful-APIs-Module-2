package com.codeexample.springbootrestapi.controllers;


import com.codeexample.springbootrestapi.dto.EmployeeDTO;
import com.codeexample.springbootrestapi.entities.EmployeeEntity;
import com.codeexample.springbootrestapi.exceptions.ResourceNotFoundException;
import com.codeexample.springbootrestapi.repositories.EmployeeRepository;
import com.codeexample.springbootrestapi.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;



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
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(name = "employeeID") int ID){
        // A Method from EmployeeService is called, which further calls a JPARepo Method through a Repository Bean
        EmployeeDTO employeeDTO = employeeService.findById(ID);
        if(employeeDTO!= null){
            return ResponseEntity.ok().body(employeeDTO);
        }
        else{
            throw new ResourceNotFoundException("Employee does not exist with ID: "+ID);
        }
    }

    //Request Params are optional, and we may or may not provide them in the URL.
    @GetMapping(path = "/allemployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) int age,
                                 @RequestParam(required = false) String name){
        return ResponseEntity.ok(employeeService.findAll()) ;

    }

    //Browser can only send GET Requests, so we need Postman for these mappings.
    @PostMapping(path = "/employees")
    // @Valid - Checks the EmployeeDTO for valid annotations.
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        EmployeeDTO savedEmployee = employeeService.save(inputEmployee);
        return new ResponseEntity<>(savedEmployee,HttpStatus.CREATED);
    }

    //Changing whole data of an employee on the backend, use PUT Mapping
    @PutMapping(path = "/employee/{employeeID}")
    public ResponseEntity<EmployeeDTO> updateEmployeebyId(@RequestBody EmployeeDTO inputEmployee, @PathVariable(name="employeeID") int empID){
        return ResponseEntity.ok(employeeService.updateEmployeeID(inputEmployee,empID));
    }

    @DeleteMapping(path = "/{employeeID}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "employeeID") int empID){
        employeeService.deleteEmployee(empID);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }

    // Partially Update details of an employee
    @PatchMapping(path = "/employees/{employeeId}" )
    public ResponseEntity<EmployeeDTO> updateEmployeeDetails(@PathVariable(name = "employeeId") int empID, Map<String,Object> fieldUpdate){
        EmployeeDTO employeeDTO = employeeService.updateEmployeeDetails(empID,fieldUpdate);
        if(employeeDTO != null){
            return ResponseEntity.ok(employeeDTO);
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }






}
