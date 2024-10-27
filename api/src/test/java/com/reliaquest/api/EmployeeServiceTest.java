package com.reliaquest.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.reliaquest.api.client.EmployeeRequest;
import com.reliaquest.api.client.EmployeeRestClient;
import com.reliaquest.api.employee.Employee;
import com.reliaquest.api.employee.EmployeeService;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRestClient employeeRestClient;

    @Autowired
    EmployeeService employeeService;

    @Test
    public void returnAllEmployees() {
        when(employeeRestClient.getEmployees()).thenReturn(mockEmployees());
        List<Employee> results = employeeService.getAllEmployees();
        assertNotNull(results);
        assertEquals(15, results.size());
    }

    @Test
    public void getEmployeeByNameAllNames() {
        when(employeeRestClient.getEmployees()).thenReturn(mockEmployees());
        List<Employee> results = employeeService.getEmployeeByName("random");
        assertNotNull(results);
        assertEquals(15, results.size());
    }

    @Test
    public void getEmployeeByNameOneName() {
        when(employeeRestClient.getEmployees()).thenReturn(mockEmployees());
        List<Employee> results = employeeService.getEmployeeByName("Name13");
        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    public void getEmployeeByIdFound() {
        Employee employee = new Employee();
        when(employeeRestClient.getEmployeeById(anyString())).thenReturn(employee);
        assertEquals(employee, employeeService.getEmployeeById(UUID.randomUUID().toString()));
    }

    @Test
    public void getEmployeeByIdNotFound() {
        Employee employee = new Employee();
        when(employeeRestClient.getEmployeeById(anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatusCode.valueOf(404)));
        assertThrows(
                HttpClientErrorException.class,
                () -> employeeService.getEmployeeById(UUID.randomUUID().toString()));
    }

    @Test
    public void returnHighestSalary() {
        when(employeeRestClient.getEmployees()).thenReturn(mockEmployees());
        int result = employeeService.getHighestSalary();
        assertEquals(10014, result);
    }

    @Test
    public void createEmployee() {
        Employee mockEmployee = mockEmployees().get(0);
        EmployeeRequest employeeRequest = new EmployeeRequest(
                mockEmployee.getName(), mockEmployee.getSalary(), mockEmployee.getAge(), mockEmployee.getTitle());
        when(employeeRestClient.createEmployee(any(employeeRequest.getClass()))).thenReturn(mockEmployee);
        assertEquals(mockEmployee, employeeService.createEmployee(employeeRequest));
    }

    @Test
    public void deleteEmployee() {
        when(employeeRestClient.deleteEmployee(any(EmployeeRequest.class))).thenReturn(true);
        when(employeeRestClient.getEmployeeById(anyString())).thenReturn(new Employee());
        assertEquals(
                "Deleted", employeeService.deleteEmployeeById(UUID.randomUUID().toString()));
    }

    @Test
    public void deleteEmployeeNotDeleted() {
        when(employeeRestClient.deleteEmployee(any(EmployeeRequest.class))).thenReturn(false);
        when(employeeRestClient.getEmployeeById(anyString())).thenReturn(new Employee());
        assertEquals(
                "Could not delete",
                employeeService.deleteEmployeeById(UUID.randomUUID().toString()));
    }

    @Test
    public void getHighestPayedSalariesByNameOnlyTen() {
        when(employeeRestClient.getEmployees()).thenReturn(mockEmployees().subList(0, 10));
        List<String> results = employeeService.getTopHighestEarningEmployeeNames();
        assertEquals(10, results.size());
        for (int i = 0; i < 10; i++) {
            assertEquals("randomName" + i, results.get(i));
        }
    }

    @Test
    public void getHighestPayedSalariesByName() {
        List<Employee> employees = mockEmployees();
        Collections.shuffle(employees);
        when(employeeRestClient.getEmployees()).thenReturn(employees);
        List<String> results = employeeService.getTopHighestEarningEmployeeNames();
        assertEquals(10, results.size());
        int offset = 5;
        for (int i = offset; i < 15; i++) {
            assertEquals("randomName" + i, results.get(i - offset));
        }
    }

    private List<Employee> mockEmployees() {
        LinkedList<Employee> employees = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            Employee employee = new Employee();
            employee.setAge(16 + i);
            employee.setId(UUID.randomUUID().toString());
            employee.setName("randomName" + i);
            employee.setSalary(10000 + i);
            employee.setEmail("randomEmail" + i);
            employee.setTitle("randomTitle" + i);
            employees.add(employee);
        }
        return employees;
    }
}
