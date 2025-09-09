package com.madaffner.tantive.itzbund.demo.service;

import com.madaffner.tantive.itzbund.demo.dao.EmployeeRepository;
import com.madaffner.tantive.itzbund.demo.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindAll() {
        // Setup the employees list
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Doe", "john.doe@example.com"));
        employees.add(new Employee("Jane", "Doe", "jane.doe@example.com"));

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.getFirst()).isEqualTo(employees.getFirst());
        assertThat(result.getLast()).isEqualTo(employees.getLast());
    }

    @Test
    public void testFindById() {
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        employee.setId(1);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Employee result = employeeService.findById(1);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(employee.getFirstName());
    }

    @Test
    public void testFindByIdNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> employeeService.findById(1));
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee("John", "Doe", "john.doe@example.com");
        employee.setId(1);

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.save(employee);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(employee.getFirstName());
    }

    @Test
    public void testDeleteEmployee() {
        employeeService.deleteById(1);
        verify(employeeRepository, times(1)).deleteById(1);
    }

}
