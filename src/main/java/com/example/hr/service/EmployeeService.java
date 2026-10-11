package com.example.hr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.hr.dto.request.EmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.enums.EmployeeStatus;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    Page<EmployeeResponse> getAllEmployees(Pageable pageable);

    Page<EmployeeResponse> searchEmployees(String keyword, Pageable pageable);

    Page<EmployeeResponse> getEmployeesByStatus(
            EmployeeStatus status, Pageable pageable);

    Page<EmployeeResponse> getEmployeesByDepartment(
            Long departmentId, Pageable pageable);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);
}

