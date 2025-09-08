package com.madaffner.tantive.itzbund.demo.rest;

import com.madaffner.tantive.itzbund.demo.entity.Employee;
import com.madaffner.tantive.itzbund.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.findById(id);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        // set id to 0 to force a save of new item
        employee.setId(0);
        return employeeService.save(employee);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        Employee foundEmployee = employeeService.findById(id);

        if(foundEmployee == null) {
            throw new RuntimeException("Unable to find an employee with id: " + id);
        }

        employeeService.deleteById(id);

        return MessageFormat.format("Deletion of employee with id {0} was successful", id);
    }


}
