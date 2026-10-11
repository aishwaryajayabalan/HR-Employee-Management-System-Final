
package com.example.hr.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EmployeeRequest(

    @NotBlank(message = "Employee number is required")
    @Size(max = 20, message = "Employee number cannot exceed 20 characters")
    String employeeNumber,

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    String lastName,

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    String email,

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    String phone,

    @NotBlank(message = "Job title is required")
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    String jobTitle,

    @NotNull(message = "Joining date is required")
    @PastOrPresent(message = "Joining date cannot be in the future")
    LocalDate joiningDate,

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    BigDecimal salary,

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be greater than zero")
    Long departmentId
) {
}
