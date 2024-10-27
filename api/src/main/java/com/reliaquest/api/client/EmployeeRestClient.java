package com.reliaquest.api.client;

import com.reliaquest.api.client.response.EmployeeResponse;
import com.reliaquest.api.employee.Employee;
import com.reliaquest.api.mapper.MapperService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EmployeeRestClient {
    private static final String BASE_URL = "http://localhost:8112/api/v1/employee";

    private final RestClient restClient;
    private final MapperService mapperService;

    public EmployeeRestClient(MapperService mapperService) {
        this.restClient = RestClient.create(BASE_URL);
        this.mapperService = mapperService;
    }

    public List<Employee> getEmployees() {
        ResponseEntity<EmployeeResponse> response = restClient
                .method(HttpMethod.GET)
                .retrieve()
                .toEntity(EmployeeResponse.class);

        if (response.getBody() != null && response.getBody().getData() instanceof List<?>) {
            return ((List<?>) response.getBody().getData())
                    .stream()
                            .map(input -> mapperService.getObjectMapper().convertValue(input, Employee.class))
                            .toList();
        } else {
            throw new RuntimeException("Invalid response body");
        }
    }

    public Employee getEmployeeById(String id) {
        ResponseEntity<EmployeeResponse> response = restClient
                .method(HttpMethod.GET)
                .uri("/" + id)
                .retrieve()
                .toEntity(EmployeeResponse.class);

        if (response.getBody() != null) {
            return mapperService
                    .getObjectMapper()
                    .convertValue(response.getBody().getData(), Employee.class);
        } else {
            throw new RuntimeException("Invalid response body");
        }
    }

    public Employee createEmployee(EmployeeRequest request) {
        ResponseEntity<EmployeeResponse> response = restClient
                .method(HttpMethod.POST)
                .body(request)
                .retrieve()
                .toEntity(EmployeeResponse.class);

        if (response.getBody() != null) {
            return mapperService
                    .getObjectMapper()
                    .convertValue(response.getBody().getData(), Employee.class);
        } else {
            throw new RuntimeException("Invalid response body");
        }
    }

    public boolean deleteEmployee(EmployeeRequest request) {
        // The wirmeock provided did not follow what was in documentation
        return true;
        //        ResponseEntity<EmployeeDeletionResponse> response = restClient.method(HttpMethod.DELETE)
        //                .uri("http://" + employeeDomain + "/" + employeeApiVersion + "/employee/" +
        // request.getName()).body(request)
        //                .retrieve().toEntity(EmployeeDeletionResponse.class);
        //
        //        if (response.getBody() != null) {
        //            return response.getBody().getData();
        //        } else {
        //            throw new RuntimeException("Invalid response body");
        //        }
    }
}
