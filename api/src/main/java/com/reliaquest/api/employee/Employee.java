package com.reliaquest.api.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
    String id;

    @JsonProperty("employee_name")
    String name;

    @JsonProperty("employee_salary")
    int salary;

    @JsonProperty("employee_age")
    int age;

    @JsonProperty("employee_title")
    String title;

    @JsonProperty("employee_email")
    String email;

    public Employee() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
