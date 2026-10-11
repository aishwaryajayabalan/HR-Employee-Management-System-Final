
package com.example.hr.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.hr.enums.LeaveStatus;

public record LeaveResponse(
    Long id,
    String leaveType,
    LocalDate startDate,
    LocalDate endDate,
    String reason,
    LeaveStatus status,
    String reviewComments,
    Long employeeId,
    String employeeName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
