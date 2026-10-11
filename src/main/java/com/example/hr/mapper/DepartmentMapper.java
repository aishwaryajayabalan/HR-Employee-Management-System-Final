
package com.example.hr.mapper;

import org.springframework.stereotype.Component;

import com.example.hr.dto.request.DepartmentRequest;
import com.example.hr.dto.response.DepartmentResponse;
import com.example.hr.entity.Department;
import com.example.hr.enums.EmployeeStatus;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequest request) {
        Department department = new Department();
        department.setDepartmentName(request.departmentName());
        department.setDepartmentCode(request.departmentCode());
        department.setDescription(request.description());
        department.setStatus(EmployeeStatus.ACTIVE);
        return department;
    }

    public void updateEntity(
            Department department,
            DepartmentRequest request) {

        department.setDepartmentName(request.departmentName());
        department.setDepartmentCode(request.departmentCode());
        department.setDescription(request.description());
    }

    public DepartmentResponse toResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentCode(),
                department.getDescription(),
                department.getStatus(),
                department.getCreatedAt(),
                department.getUpdatedAt()
        );
    }
}

