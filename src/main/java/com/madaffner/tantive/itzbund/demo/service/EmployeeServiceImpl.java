package com.madaffner.tantive.itzbund.demo.service;

import com.madaffner.tantive.itzbund.demo.dao.EmployeeRepository;
import com.madaffner.tantive.itzbund.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if(foundEmployee.isEmpty()) {
            throw new RuntimeException("No employee with id - " + id);
        }

        return foundEmployee.get();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}
