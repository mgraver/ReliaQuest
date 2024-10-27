package com.reliaquest.api.employee;

import com.reliaquest.api.client.EmployeeRequest;
import com.reliaquest.api.client.EmployeeRestClient;
import com.reliaquest.api.mapper.MapperService;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    EmployeeRestClient employeeRestClient;
    MapperService mapperService;

    public EmployeeService(EmployeeRestClient employeeRestClient, MapperService mapperService) {
        this.employeeRestClient = employeeRestClient;
        this.mapperService = mapperService;
    }

    public List<Employee> getAllEmployees() {
        return employeeRestClient.getEmployees();
    }

    public List<Employee> getEmployeeByName(String name) {
        List<Employee> employees = employeeRestClient.getEmployees();
        return employees.stream()
                .filter(employee -> employee.getName().contains(name))
                .toList();
    }

    public Employee getEmployeeById(String id) {
        return employeeRestClient.getEmployeeById(id);
    }

    public Integer getHighestSalary() {
        int highestSalary = -1;
        List<Employee> employees = employeeRestClient.getEmployees();

        for (Employee employee : employees) {
            if (employee.getSalary() > highestSalary) {
                highestSalary = employee.getSalary();
            }
        }
        return highestSalary == -1 ? null : highestSalary;
    }

    public List<String> getTopHighestEarningEmployeeNames() {
        List<Employee> employees = employeeRestClient.getEmployees();
        if (employees.isEmpty()) {
            return new ArrayList<>();
        }

        int min = employees.get(0).getSalary();
        LinkedHashMap<Integer, Employee> topGrossers = new LinkedHashMap<Integer, Employee>();
        for (Employee employee : employees) {
            if (topGrossers.size() < 10) {
                topGrossers.put(employee.getSalary(), employee);
                min = Math.min(min, employee.getSalary());
                continue;
            }

            if (employee.getSalary() > min) {
                topGrossers.put(employee.getSalary(), employee);
                topGrossers.remove(min);
                min = Collections.min(topGrossers.keySet());
            }
        }

        return topGrossers.values().stream()
                .sorted(Comparator.comparingInt(Employee::getSalary))
                .map(Employee::getName)
                .toList();
    }

    public Employee createEmployee(Object input) {
        // Would validate/sanitise data here but going to skip that.
        EmployeeRequest request = mapperService.getObjectMapper().convertValue(input, EmployeeRequest.class);
        return employeeRestClient.createEmployee(request);
    }

    public String deleteEmployeeById(String id) {
        Employee employeeToDelete = employeeRestClient.getEmployeeById(id);
        boolean deleted =
                employeeRestClient.deleteEmployee(new EmployeeRequest(employeeToDelete.getName(), null, null, null));
        return deleted ? "Deleted" : "Could not delete";
    }
}
