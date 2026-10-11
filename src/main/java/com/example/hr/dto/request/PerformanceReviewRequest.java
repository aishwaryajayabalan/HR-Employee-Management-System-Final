
package com.example.hr.dto.request;

import java.time.LocalDate;

import com.example.hr.enums.ReviewRating;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PerformanceReviewRequest(

    @NotNull(message = "Review date is required")
    @PastOrPresent(message = "Review date cannot be in the future")
    LocalDate reviewDate,

    @NotNull(message = "Review rating is required")
    ReviewRating rating,

    @Size(max = 1000, message = "Comments cannot exceed 1000 characters")
    String comments,

    @Size(max = 100, message = "Reviewer name cannot exceed 100 characters")
    String reviewerName,

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be greater than zero")
    Long employeeId
) {
}
