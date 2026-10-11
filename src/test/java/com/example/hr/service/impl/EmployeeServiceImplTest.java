
package com.example.hr.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hr.entity.Employee;
import com.example.hr.exception.BusinessRuleException;
import com.example.hr.exception.ResourceNotFoundException;
import com.example.hr.mapper.EmployeeMapper;
import com.example.hr.repository.DepartmentRepository;
import com.example.hr.repository.EmployeeRepository;
import com.example.hr.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);
    }

    @Test
    void getEmployeeById_whenEmployeeExists_returnsEmployeeResponse() {
        when(employeeRepository.findByIdWithDepartment(1L))
                .thenReturn(Optional.of(employee));

        employeeService.getEmployeeById(1L);

        verify(employeeRepository).findByIdWithDepartment(1L);
        verify(employeeMapper).toResponse(employee);
    }

    @Test
    void getEmployeeById_whenEmployeeDoesNotExist_throwsNotFoundException() {
        when(employeeRepository.findByIdWithDepartment(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(99L));
    }

    @Test
    void getEmployeeById_whenIdIsInvalid_throwsBusinessRuleException() {
        assertThrows(BusinessRuleException.class,
                () -> employeeService.getEmployeeById(0L));
    }
}
