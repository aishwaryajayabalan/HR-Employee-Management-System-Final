
package com.example.hr.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LeaveRequestDto(

    @NotBlank(message = "Leave type is required")
    @Size(max = 50, message = "Leave type cannot exceed 50 characters")
    String leaveType,

    @NotNull(message = "Start date is required")
    LocalDate startDate,

    @NotNull(message = "End date is required")
    LocalDate endDate,

    @NotBlank(message = "Reason is required")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    String reason,

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be greater than zero")
    Long employeeId
) {
    @AssertTrue(message = "End date must be on or after start date")
    public boolean isDateRangeValid() {
        return startDate == null || endDate == null
                || !endDate.isBefore(startDate);
    }
}

