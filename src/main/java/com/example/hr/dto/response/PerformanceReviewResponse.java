
package com.example.hr.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.hr.enums.ReviewRating;

public record PerformanceReviewResponse(
    Long id,
    LocalDate reviewDate,
    ReviewRating rating,
    String comments,
    String reviewerName,
    Long employeeId,
    String employeeName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
