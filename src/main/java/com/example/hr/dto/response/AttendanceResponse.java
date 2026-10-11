
package com.example.hr.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.hr.enums.AttendanceStatus;

public record AttendanceResponse(
    Long id,
    LocalDate attendanceDate,
    AttendanceStatus status,
    Double workingHours,
    String remarks,
    Long employeeId,
    String employeeName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}

