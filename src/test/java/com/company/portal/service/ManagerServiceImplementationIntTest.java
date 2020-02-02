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
public class ManagerServiceImplementationIntTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ManagerServiceImplementation mangService;


    Employee e1 = new Employee();
    Employee e2 = new Employee();

    @Before
    public void createEmployeeObjects() {
        e1.setName("john ");
        e1.setDepartment("sales");
        e1.setDesignation("engineer");
        e1.setSalary(2000);

        e2.setName("harry");
        e2.setDepartment("sales");
        e2.setDesignation("manager");
        e2.setSalary(5000);
        employeeRepository.save(e1);
        employeeRepository.save(e2);
    }

    @Test
    public void getEmployees() {
        List<Employee> managers = mangService.getEmployees();
        for (Employee employee : managers)
            assertEquals("manager", employee.getDesignation());
    }

    @Test
    public void getEmployeeIdWithIdIfExists() {
        List<Employee> managers = mangService.getEmployees();
        int id = 1;
        for (Employee employee : managers)
            id = employee.getId();
        Employee employee = mangService.getEmployeeById(id);
        assertEquals(e2, employee);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void throwExceptionIfEmployeeWithIdNotFound() {
        mangService.getEmployeeById(200);
    }

    @Test
    public void addEmployeeIfNotExists() {
        Employee newEmployee = new Employee();
        newEmployee.setName("mike");
        newEmployee.setDepartment("sales");
        newEmployee.setSalary(5000);
        Employee employee = mangService.addNewEmployee(newEmployee);
        newEmployee.setId(employee.getId());
        employee = employeeRepository.findById(employee.getId()).get();
        assertNotNull(employee);
        newEmployee.setDesignation("manager");
        assertEquals(newEmployee, employee);
    }

    @Test(expected = EmployeeAlreadyExistsException.class)
    public void throwExceptionIfEmployeeAlreadyExists() {
        mangService.addNewEmployee(e2);
        mangService.addNewEmployee(e2);
    }

    @Test
    public void shouldUpdateIfFound() {
        Employee employee = new Employee();
        employee.setName("patrick");
        employee.setDepartment("sales");
        employee.setDesignation("manager");
        employee.setSalary(6000);
        mangService.updateEmployee(employee, 1);
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
        employee.setDesignation("manager");
        employee.setSalary(6000);
        Employee updatedEmployee = mangService.updateEmployee(employee, 13);
        employee.setId(updatedEmployee.getId());
        assertEquals(employee, updatedEmployee);
    }

    @Test
    public void shouldDeleteEmployeeIfFound() {
        mangService.deleteEmployeeById(1);
        Optional<Employee> employee = employeeRepository.findById(1);
        assertFalse(employee.isPresent());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void throwExceptionWhenEmployeeNotFound() {
        mangService.deleteEmployeeById(200);
        Employee employee = employeeRepository.findById(200).get();
        assertNull(employee);
    }
}