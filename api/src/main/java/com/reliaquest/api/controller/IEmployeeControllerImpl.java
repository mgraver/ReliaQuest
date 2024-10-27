package com.reliaquest.api.controller;

import com.reliaquest.api.client.EmployeeRestClient;
import com.reliaquest.api.employee.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class IEmployeeControllerImpl implements IEmployeeController {

    @Autowired
    EmployeeRestClient employeeRestClient;

    @Autowired
    EmployeeService employeeService;

    @Override
    public ResponseEntity<List> getAllEmployees() {
        return ResponseEntity.ofNullable(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List> getEmployeesByNameSearch(String searchString) {
        return ResponseEntity.ofNullable(employeeService.getEmployeeByName(searchString));
    }

    @Override
    public ResponseEntity getEmployeeById(String id) {
        return ResponseEntity.ofNullable(employeeService.getEmployeeById(id));
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ofNullable(employeeService.getHighestSalary());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ofNullable(employeeService.getTopHighestEarningEmployeeNames());
    }

    @Override
    public ResponseEntity createEmployee(Object employeeInput) {
        return ResponseEntity.ofNullable(employeeService.createEmployee(employeeInput));
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return ResponseEntity.ofNullable(employeeService.deleteEmployeeById(id));
    }
}
