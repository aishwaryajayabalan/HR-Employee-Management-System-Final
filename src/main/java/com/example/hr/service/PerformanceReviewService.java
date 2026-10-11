package com.example.hr.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.hr.dto.request.PerformanceReviewRequest;
import com.example.hr.dto.response.PerformanceReviewResponse;
import com.example.hr.enums.ReviewRating;

public interface PerformanceReviewService {

    PerformanceReviewResponse createReview(PerformanceReviewRequest request);

    PerformanceReviewResponse getReviewById(Long id);

    Page<PerformanceReviewResponse> getAllReviews(Pageable pageable);

    Page<PerformanceReviewResponse> getReviewsByEmployee(
            Long employeeId, Pageable pageable);

    Page<PerformanceReviewResponse> getReviewsByRating(
            ReviewRating rating, Pageable pageable);

    Page<PerformanceReviewResponse> getReviewsByDateRange(
            LocalDate startDate, LocalDate endDate, Pageable pageable);

    PerformanceReviewResponse updateReview(
            Long id, PerformanceReviewRequest request);

    void deleteReview(Long id);
}
