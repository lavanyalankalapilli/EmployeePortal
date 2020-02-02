package com.company.portal.controller;

import com.company.portal.model.Employee;
import com.company.portal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public class EmployeeController {

    @Autowired
    @Qualifier("engineerServiceImplementation")
    EmployeeService engService;

    @Autowired
    @Qualifier("managerServiceImplementation")
    EmployeeService manService;

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees() {
        List<Employee> employees = engService.getEmployees();
        employees.addAll(manService.getEmployees());
        return employees;
    }

    @GetMapping(value = "/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable int id) {
        return engService.getEmployeeById(id);
    }

    @PostMapping(value = "/employees/engineers")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEngineer(@RequestBody Employee e) {
        return engService.addNewEmployee(e);
    }

    @PostMapping(value = "/employees/managers")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createManager(@RequestBody Employee e) {
        return manService.addNewEmployee(e);
    }

    @PutMapping(value = "/employees/engineers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Employee updateEngineer(@RequestBody Employee e, @PathVariable int id) {
        return engService.updateEmployee(e, id);
    }

    @PutMapping(value = "/employees/managers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Employee updateManager(@RequestBody Employee e, @PathVariable int id) {
        return manService.updateEmployee(e, id);
    }

    @DeleteMapping("/employees/engineers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delEngineer(@PathVariable int id) {
        engService.deleteEmployeeById(id);
    }

    @DeleteMapping("/employees/managers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delManager(@PathVariable int id) {
        manService.deleteEmployeeById(id);
    }
}