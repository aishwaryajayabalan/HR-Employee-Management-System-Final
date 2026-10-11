
package com.example.hr.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.hr.dto.request.DepartmentRequest;
import com.example.hr.dto.response.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse getDepartmentById(Long id);

    Page<DepartmentResponse> getAllDepartments(Pageable pageable);

    List<DepartmentResponse> searchDepartments(String name);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    void deleteDepartment(Long id);
}
