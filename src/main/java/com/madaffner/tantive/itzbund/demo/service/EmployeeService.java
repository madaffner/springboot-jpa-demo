package com.madaffner.tantive.itzbund.demo.service;

import com.madaffner.tantive.itzbund.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int id);

    Employee save(Employee employee);

    void deleteById(int id);

}
