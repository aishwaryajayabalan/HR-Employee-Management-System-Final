
package com.example.hr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hr.dto.request.EmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.enums.EmployeeStatus;
import com.example.hr.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {
        employeeResponse =
                org.mockito.Mockito.mock(EmployeeResponse.class);
    }

    @Test
    void getEmployeeById_returnsOk() {
        when(employeeService.getEmployeeById(1L))
                .thenReturn(employeeResponse);

        ResponseEntity<EmployeeResponse> response =
                employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeResponse, response.getBody());
        verify(employeeService).getEmployeeById(1L);
    }

    @Test
    void getAllEmployees_returnsOk() {
        Pageable pageable = PageRequest.of(
                0, 10, Sort.by("lastName").ascending());

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(employeeResponse));

        when(employeeService.getAllEmployees(pageable))
                .thenReturn(page);

        ResponseEntity<Page<EmployeeResponse>> response =
                employeeController.getAllEmployees(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(employeeService).getAllEmployees(pageable);
    }

    @Test
    void searchEmployees_returnsOk() {
        Pageable pageable = PageRequest.of(
                0, 5, Sort.by("lastName").ascending());

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(employeeResponse));

        when(employeeService.searchEmployees("Arun", pageable))
                .thenReturn(page);

        ResponseEntity<Page<EmployeeResponse>> response =
                employeeController.searchEmployees("Arun", 0, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(employeeService).searchEmployees("Arun", pageable);
    }

    @Test
    void getEmployeesByStatus_returnsOk() {
        Pageable pageable = PageRequest.of(
                0, 5, Sort.by("lastName").ascending());

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(employeeResponse));

        when(employeeService.getEmployeesByStatus(
                EmployeeStatus.ACTIVE, pageable))
                .thenReturn(page);

        ResponseEntity<Page<EmployeeResponse>> response =
                employeeController.getEmployeesByStatus(
                        EmployeeStatus.ACTIVE, 0, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());

        verify(employeeService).getEmployeesByStatus(
                EmployeeStatus.ACTIVE, pageable);
    }

    @Test
    void getEmployeesByDepartment_returnsOk() {
        Pageable pageable = PageRequest.of(
                0, 5, Sort.by("lastName").ascending());

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(employeeResponse));

        when(employeeService.getEmployeesByDepartment(3L, pageable))
                .thenReturn(page);

        ResponseEntity<Page<EmployeeResponse>> response =
                employeeController.getEmployeesByDepartment(3L, 0, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(employeeService).getEmployeesByDepartment(3L, pageable);
    }

    @Test
    void createEmployee_returnsCreated() {
        EmployeeRequest request =
                org.mockito.Mockito.mock(EmployeeRequest.class);

        when(employeeService.createEmployee(request))
                .thenReturn(employeeResponse);

        ResponseEntity<EmployeeResponse> response =
                employeeController.createEmployee(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeResponse, response.getBody());
        verify(employeeService).createEmployee(request);
    }

    @Test
    void updateEmployee_returnsOk() {
        EmployeeRequest request =
                org.mockito.Mockito.mock(EmployeeRequest.class);

        when(employeeService.updateEmployee(1L, request))
                .thenReturn(employeeResponse);

        ResponseEntity<EmployeeResponse> response =
                employeeController.updateEmployee(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeResponse, response.getBody());
        verify(employeeService).updateEmployee(1L, request);
    }

    @Test
    void deleteEmployee_returnsNoContent() {
        doNothing().when(employeeService).deleteEmployee(1L);

        ResponseEntity<Void> response =
                employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService).deleteEmployee(1L);
    }
}
