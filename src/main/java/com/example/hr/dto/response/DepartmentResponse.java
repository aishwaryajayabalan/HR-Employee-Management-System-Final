
package com.example.hr.dto.response;

import java.time.LocalDateTime;

import com.example.hr.enums.EmployeeStatus;

public record DepartmentResponse(
    Long id,
    String departmentName,
    String departmentCode,
    String description,
    EmployeeStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
