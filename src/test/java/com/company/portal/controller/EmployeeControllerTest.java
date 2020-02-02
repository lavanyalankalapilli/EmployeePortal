package com.company.portal.controller;

import com.company.portal.model.Employee;
import com.company.portal.service.EngineerServiceImplementation;
import com.company.portal.service.ManagerServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController controller;

    @Mock
    EngineerServiceImplementation engService;

    @Mock
    ManagerServiceImplementation mangService;

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
    }

    @Test
    public void getEmployees() {
        List<Employee> engineers = new ArrayList<>();
        engineers.add(e1);
        when(engService.getEmployees()).thenReturn(engineers);
        List<Employee> managers = new ArrayList<>();
        managers.add(e2);
        when(mangService.getEmployees()).thenReturn(managers);
        List<Employee> employees = controller.getEmployees();
        assertEquals(2, employees.size());
        assertEquals(e1, employees.get(0));
        assertEquals(e2, employees.get(1));
    }

    @Test
    public void getEmployee() {
        when(engService.getEmployeeById(1)).thenReturn(e1);
        Employee employee = controller.getEmployee(1);
        assertEquals(e1, employee);
    }

    @Test
    public void createEngineer() {
        when(engService.addNewEmployee(e1)).thenReturn(e1);
        Employee employee = controller.createEngineer(e1);
        assertEquals(e1, employee);
    }

    @Test
    public void createManager() {
        when(mangService.addNewEmployee(e2)).thenReturn(e2);
        Employee employee = controller.createManager(e2);
        assertEquals(e2, employee);
    }

    @Test
    public void updateEngineer() {
        when(engService.updateEmployee(e1, 1)).thenReturn(e1);
        Employee employee = controller.updateEngineer(e1, 1);
        assertEquals(e1, employee);
    }

    @Test
    public void updateManager() {
        when(mangService.updateEmployee(e2, 2)).thenReturn(e2);
        Employee employee = controller.updateManager(e2, 2);
        assertEquals(e2, employee);
    }

}
