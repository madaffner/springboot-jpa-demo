package com.madaffner.tantive.itzbund.demo.rest;

import com.madaffner.tantive.itzbund.demo.entity.Employee;
import com.madaffner.tantive.itzbund.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for EmployeeRestController
 *
 * Tests basically make sure that the rest controller calls the service
 * and returns the correct result.
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeRestControllerTest {

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Mock
    private EmployeeService employeeService;

    @Test
    public void testFindAll() {
        // Setup the employees list
        List<Employee> employees = new ArrayList<>();

        Employee firstEmployee = new Employee("John", "Doe", "john.doe@example.com");
        firstEmployee.setId(1);
        employees.add(firstEmployee);

        Employee secondEmployee = new Employee("Alex", "Dober", "alex.dober@example.com");
        secondEmployee.setId(2);
        employees.add(secondEmployee);

        // Mock the findAll call of the service to return the mock list
        when(employeeService.findAll()).thenReturn(employees);


        List<Employee> result = employeeRestController.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getFirstName()).isEqualTo(firstEmployee.getFirstName());
        assertThat(result.get(1).getFirstName()).isEqualTo(secondEmployee.getFirstName());

    }

    @Test
    public void testFindById() {
        // setup the employeeService mock
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        employee.setId(1);

        when(employeeService.findById(1)).thenReturn(employee);

        Employee result = employeeRestController.getEmployeeById(1);
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(employee.getFirstName());
    }

    @Test
    public void testAddEmployee() {
        // setup the employeeService mock
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        employee.setId(1);

        when(employeeService.save(employee)).thenReturn(employee);

        Employee result = employeeRestController.addEmployee(employee);
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(employee.getFirstName());
    }

    @Test
    public void testDeleteEmployee() {
        // setup the employeeService mock
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        employee.setId(1);

        when(employeeService.findById(1)).thenReturn(employee);

        String message = employeeRestController.deleteEmployee(1);

        assertThat(message).isEqualTo("Deletion of employee with id 1 was successful");
    }

}
