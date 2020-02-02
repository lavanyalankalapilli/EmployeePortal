package com.company.portal.service;

import java.util.List;
import java.util.Optional;

import com.company.portal.exception.EmployeeAlreadyExistsException;
import com.company.portal.exception.EmployeeNotFoundException;
import com.company.portal.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.company.portal.model.Employee;

@Service
@Qualifier("engineerService")
public class EngineerServiceImplementation implements EmployeeService {

    static final String DESIGNATION = "engineer";
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findByDesignation(DESIGNATION);
    }

    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> emp = employeeRepository.findById(id);
        if (emp.isPresent()) {
            return emp.get();
        }
        throw new EmployeeNotFoundException("Employee with id " + id + " not found");
    }

    @Override
    public Employee addNewEmployee(Employee emp) {
        Optional<Employee> employee = employeeRepository.findById(emp.getId());
        if (employee.isPresent()) {
            throw new EmployeeAlreadyExistsException("Employee with id " + emp.getId() + " already exists");
        } else {
            emp.setDesignation(DESIGNATION);
            emp = employeeRepository.save(emp);
        }
        return emp;
    }

    @Override
    public Employee updateEmployee(Employee emp, int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            employee1.setDepartment(emp.getDepartment());
            employee1.setDesignation(emp.getDesignation());
            employee1.setName(emp.getName());
            employee1.setSalary(emp.getSalary());
            return employeeRepository.save(employee1);

        } else {
            emp.setId(id);
            return employeeRepository.save(emp);
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

}
