package com.company.portal.repositories;

import com.company.portal.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findByDesignation(String designation);
}
