package com.codeexample.springbootrestapi.services;


import com.codeexample.springbootrestapi.dto.EmployeeDTO;
import com.codeexample.springbootrestapi.entities.EmployeeEntity;
import com.codeexample.springbootrestapi.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.ScopedValue;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    public boolean existsById(int empID){
        return employeeRepository.existsById(empID);
    }

    public void deleteEmployee(int empID) {
        employeeRepository.deleteById(empID);
    }

    public EmployeeDTO updateEmployeeDetails(int empID, Map<String,Object> fieldToBeUpdated){
        EmployeeEntity employeeEntity = employeeRepository.findById(empID).orElse(null);
        if(existsById(empID)) {
            fieldToBeUpdated.forEach((fieldName,fieldValue) -> {
                Field fieldToUpdate = ReflectionUtils.findField(EmployeeEntity.class,fieldName);
                /* ReflectionUtils.findField goes to the targeted EmployeeEntity class and then finds similar fields
                provided by the user, for example if user provides "name", it goes to Entity class then finds the name
                field and puts it inside a field object called fieldToUpdate.
                 */
                fieldToUpdate.setAccessible(true);
                /* fieldToUpdate basically allows for modification of the value of this field, so the value of the name
                in the previous example can be set again.
                */
                ReflectionUtils.setField(fieldToUpdate,employeeEntity,fieldValue);
                /* setField method takes the Field object, Target Class and fieldValue from the map and modifies the
                field object and sets it with fieldValue's new value. This happens for all Strings provided in the Map
                due to the forEach loop.
                 */
            });
        }
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
