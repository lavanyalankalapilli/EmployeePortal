package com.company.portal.repositories;

import com.company.portal.model.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    EmployeeRepository employeeRepository;

    Employee e1 = new Employee();
    Employee e2 = new Employee();

    @Before
    public void createEmpObjects() {
        e1.setName("john ");
        e1.setDepartment("sales");
        e1.setDesignation("engineer");
        e1.setSalary(2000);

        e2.setName("harry");
        e2.setDepartment("sales");
        e2.setDesignation("manager");
        e2.setSalary(5000);
    }

    @Test
    public void should_have_zero_entries() {
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        assertEquals(0, employees.size());
    }

    @Test
    public void insertEntry_tableNotEmpty() {
        testEntityManager.persist(e1);
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        assertEquals(1, employees.size());
    }

    @Test
    public void insertEntry_checkInsertedEntryExists() {
        testEntityManager.persist(e1);
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals(e1, employees.get(0));
    }

    @Test
    public void checkFindById() {
        Employee insertedEmployee = testEntityManager.persist(e1);
        Optional<Employee> employee = employeeRepository.findById(insertedEmployee.getId());
        assertTrue(employee.isPresent());
        assertEquals(e1, employee.get());
    }

    @Test
    public void checkFindByDesignation() {
        testEntityManager.persist(e1);
        testEntityManager.persist(e2);
        List<Employee> managers = employeeRepository.findByDesignation("manager");
        List<Employee> engineers = employeeRepository.findByDesignation("engineer");
        assertEquals(1, managers.size());
        assertEquals(1, engineers.size());
        assertEquals(e1, engineers.get(0));
        assertEquals(e2, managers.get(0));
    }

    @Test
    public void checkUpdate() {
        Employee insertedEmployee = testEntityManager.persist(e1);
        e1.setDepartment("research");
        testEntityManager.merge(e1);
        Optional<Employee> employee = employeeRepository.findById(insertedEmployee.getId());
        assertTrue(employee.isPresent());
        assertEquals("research", employee.get().getDepartment());
    }

    @Test
    public void checkDelete() {
        Employee insertedEmployee = testEntityManager.persist(e1);
        testEntityManager.persist(e2);
        List<Employee> employees = (List<Employee>) employeeRepository.findAll();
        assertEquals(2, employees.size());
        testEntityManager.remove(insertedEmployee);
        employees = (List<Employee>) employeeRepository.findAll();
        assertEquals(1, employees.size());
    }
}
