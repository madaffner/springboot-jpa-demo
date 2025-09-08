package com.madaffner.tantive.itzbund.demo.dao;

import com.madaffner.tantive.itzbund.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EmployeeRepository extends JpaRepository
 * Repository for the Employee entity to perform database operations
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Extending JpaRepository automatically generates the most commonly used methods
    // such as findAll(), findById(), save(), delete(), etc.

}
