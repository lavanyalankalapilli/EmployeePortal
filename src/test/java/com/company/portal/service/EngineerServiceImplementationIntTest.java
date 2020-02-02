package com.company.portal.service;

import com.company.portal.exception.EmployeeAlreadyExistsException;
import com.company.portal.exception.EmployeeNotFoundException;
import com.company.portal.model.Employee;
import com.company.portal.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class EngineerServiceImplementationIntTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EngineerServiceImplementation engService;

    Employee e1 = new Employee();
    Employee e2 = new Employee();

    @Before
    public void createEmployeeObjects() {
        e1.setId(1);
        e1.setName("john ");
        e1.setDepartment("sales");
        e1.setDesignation("engineer");
        e1.setSalary(2000);

        e2.setId(2);
        e2.setName("harry");
        e2.setDepartment("sales");
        e2.setDesignation("manager");
        e2.setSalary(5000);
        employeeRepository.save(e1);
        employeeRepository.save(e2);
    }

    @Test
    public void getEmployees() {
        List<Employee> engineers = engService.getEmployees();
        assertEquals(1, engineers.size());
    }

    @Test
    public void getEmployeeIdWithIdIfExists() {
        Employee employee = engService.getEmployeeById(1);
        assertEquals(e1, employee);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void throwExceptionIfEmployeeWithIdNotFound() {
        engService.getEmployeeById(10);
    }

    @Test
    public void addEmployeeIfNotExists() {
        Employee newEmployee = new Employee();
        newEmployee.setName("mike");
        newEmployee.setDepartment("sales");
        newEmployee.setSalary(5000);
        Employee employee = engService.addNewEmployee(newEmployee);
        newEmployee.setId(employee.getId());
        employee = employeeRepository.findById(employee.getId()).get();
        assertNotNull(employee);
        newEmployee.setDesignation("engineer");
        assertEquals(newEmployee, employee);
    }

    @Test(expected = EmployeeAlreadyExistsException.class)
    public void throwExceptionIfEmployeeAlreadyExists() {
        engService.addNewEmployee(e1);
    }

    @Test
    public void shouldUpdateIfFound() {
        Employee employee = new Employee();
        employee.setName("patrick");
        employee.setDepartment("sales");
        employee.setDesignation("engineer");
        employee.setSalary(6000);
        engService.updateEmployee(employee, 1);
        Optional<Employee> updatedEmployee = employeeRepository.findById(1);
        assertTrue(updatedEmployee.isPresent());
        employee.setId(1);
        assertEquals(employee, updatedEmployee.get());
    }

    @Test
    public void shouldAddWhenNotFound() {
        Employee employee = new Employee();
        employee.setName("patrick");
        employee.setDepartment("sales");
        employee.setDesignation("engineer");
        employee.setSalary(6000);
        engService.updateEmployee(employee, 3);
        Employee updatedEmployee = employeeRepository.findById(3).get();
        assertNotNull(updatedEmployee);
        assertEquals(employee, updatedEmployee);
    }

    @Test
    public void shouldDeleteEmployeeIfFound() {
        engService.deleteEmployeeById(2);
        Optional<Employee> employee = employeeRepository.findById(2);
        assertFalse(employee.isPresent());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void throwExceptionWhenEmployeeNotFound() {
        engService.deleteEmployeeById(10);
        Employee employee = employeeRepository.findById(10).get();
        assertNull(employee);
    }
}