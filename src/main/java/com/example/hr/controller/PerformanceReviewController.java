package com.example.hr.controller;

import java.time.LocalDate;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hr.dto.request.PerformanceReviewRequest;
import com.example.hr.dto.response.PerformanceReviewResponse;
import com.example.hr.enums.ReviewRating;
import com.example.hr.service.PerformanceReviewService;

@RestController
@RequestMapping("/api/performance-reviews")
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;

    public PerformanceReviewController(
            PerformanceReviewService performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @PostMapping
    public ResponseEntity<PerformanceReviewResponse> createReview(
            @Valid @RequestBody PerformanceReviewRequest request) {

        PerformanceReviewResponse response =
                performanceReviewService.createReview(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReviewResponse> getReviewById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                performanceReviewService.getReviewById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PerformanceReviewResponse>> getAllReviews(
            @PageableDefault(size = 10, sort = "reviewDate",
                    direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                performanceReviewService.getAllReviews(pageable));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<PerformanceReviewResponse>>
            getReviewsByEmployee(
                    @PathVariable Long employeeId,
                    @PageableDefault(size = 10, sort = "reviewDate",
                            direction = org.springframework.data.domain.Sort.Direction.DESC)
                    Pageable pageable) {

        return ResponseEntity.ok(
                performanceReviewService.getReviewsByEmployee(
                        employeeId, pageable));
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<Page<PerformanceReviewResponse>>
            getReviewsByRating(
                    @PathVariable ReviewRating rating,
                    @PageableDefault(size = 10, sort = "reviewDate",
                            direction = org.springframework.data.domain.Sort.Direction.DESC)
                    Pageable pageable) {

        return ResponseEntity.ok(
                performanceReviewService.getReviewsByRating(
                        rating, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<PerformanceReviewResponse>>
            getReviewsByDateRange(
                    @RequestParam
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate startDate,
                    @RequestParam
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate endDate,
                    @PageableDefault(size = 10, sort = "reviewDate",
                            direction = org.springframework.data.domain.Sort.Direction.DESC)
                    Pageable pageable) {

        return ResponseEntity.ok(
                performanceReviewService.getReviewsByDateRange(
                        startDate, endDate, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceReviewResponse> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody PerformanceReviewRequest request) {

        return ResponseEntity.ok(
                performanceReviewService.updateReview(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

        performanceReviewService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }
}
