package com.reliaquest.api.client.response;

public class EmployeeResponse {
    private final Object data;
    private final String status;

    public EmployeeResponse(Object data, String status) {
        this.data = data;
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
