
package com.example.hr.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.hr.enums.EmployeeStatus;

public record EmployeeResponse(
    Long id,
    String employeeNumber,
    String firstName,
    String lastName,
    String email,
    String phone,
    String jobTitle,
    LocalDate joiningDate,
    BigDecimal salary,
    EmployeeStatus status,
    Long departmentId,
    String departmentName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
