package com.codeexample.springbootrestapi.services;


import com.codeexample.springbootrestapi.dto.EmployeeDTO;
import com.codeexample.springbootrestapi.entities.EmployeeEntity;
import com.codeexample.springbootrestapi.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ScopedValue;
import java.util.List;
import java.util.stream.Collectors;

@Service
// Service Layer acts as an intermediary between Controller and Repository Layer
public class EmployeeService {

    // Injecting Repository
    private final EmployeeRepository employeeRepository;
    // Injecting ModelMapper
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    // Declaring Methods that further call inbuilt methods from JPARepository
    public EmployeeDTO findById(int id) {
        return modelMapper.map(employeeRepository.findById(id).orElse(null), EmployeeDTO.class);
    }

    public List<EmployeeDTO> findAll() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .toList();

    }

    public EmployeeDTO save(EmployeeDTO inputEmployee) {
        EmployeeEntity toSave = modelMapper.map(inputEmployee,EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(toSave),EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeID(EmployeeDTO inputEmployee,int empID) {
        inputEmployee.setId((long) empID);
        EmployeeEntity toUpdate = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity returnedEntity = employeeRepository.save(toUpdate);
        return modelMapper.map(returnedEntity, EmployeeDTO.class);
    }

    public void deleteEmployee(int empID) {
        employeeRepository.deleteById(empID);
    }
}
