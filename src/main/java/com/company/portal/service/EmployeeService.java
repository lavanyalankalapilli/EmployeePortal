package com.company.portal.service;

import java.util.List;

import com.company.portal.model.Employee;

public interface EmployeeService {
    public List<Employee> getEmployees();

    public Employee getEmployeeById(int id);

    public Employee addNewEmployee(Employee emp);

    public Employee updateEmployee(Employee emp, int id);

    public void deleteEmployeeById(int id);

}