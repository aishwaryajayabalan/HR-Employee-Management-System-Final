
package com.example.hr.dto.request;

import java.time.LocalDate;

import com.example.hr.enums.AttendanceStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record AttendanceRequest(

    @NotNull(message = "Attendance date is required")
    LocalDate attendanceDate,

    @NotNull(message = "Attendance status is required")
    AttendanceStatus status,

    @PositiveOrZero(message = "Working hours cannot be negative")
    Double workingHours,

    @Size(max = 255, message = "Remarks cannot exceed 255 characters")
    String remarks,

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be greater than zero")
    Long employeeId
) {
}
