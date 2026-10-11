package com.example.hr.service.impl;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.dto.request.PerformanceReviewRequest;
import com.example.hr.dto.response.PerformanceReviewResponse;
import com.example.hr.entity.Employee;
import com.example.hr.entity.PerformanceReview;
import com.example.hr.exception.BusinessRuleException;
import com.example.hr.exception.ResourceNotFoundException;
import com.example.hr.mapper.PerformanceReviewMapper;
import com.example.hr.repository.EmployeeRepository;
import com.example.hr.repository.PerformanceReviewRepository;
import com.example.hr.service.PerformanceReviewService;

@Service
@Transactional
public class PerformanceReviewServiceImpl
        implements PerformanceReviewService {

    private static final Logger logger =
            LoggerFactory.getLogger(PerformanceReviewServiceImpl.class);

    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewMapper performanceReviewMapper;

    public PerformanceReviewServiceImpl(
            PerformanceReviewRepository performanceReviewRepository,
            EmployeeRepository employeeRepository,
            PerformanceReviewMapper performanceReviewMapper) {

        this.performanceReviewRepository = performanceReviewRepository;
        this.employeeRepository = employeeRepository;
        this.performanceReviewMapper = performanceReviewMapper;
    }

    @Override
    public PerformanceReviewResponse createReview(
            PerformanceReviewRequest request) {

        validateReviewDate(request.reviewDate());

        Employee employee = findEmployee(request.employeeId());

        PerformanceReview review =
                performanceReviewMapper.toEntity(request);

        review.setEmployee(employee);

        PerformanceReview savedReview =
                performanceReviewRepository.save(review);

        logger.info("Performance review created with ID: {}",
                savedReview.getId());

        return performanceReviewMapper.toResponse(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public PerformanceReviewResponse getReviewById(Long id) {

        return performanceReviewMapper.toResponse(findReview(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerformanceReviewResponse> getAllReviews(
            Pageable pageable) {

        return performanceReviewRepository.findAll(pageable)
                .map(performanceReviewMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerformanceReviewResponse> getReviewsByEmployee(
            Long employeeId, Pageable pageable) {

        findEmployee(employeeId);

        return performanceReviewRepository.findByEmployeeId(
                        employeeId, pageable)
                .map(performanceReviewMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerformanceReviewResponse> getReviewsByRating(
            com.example.hr.enums.ReviewRating rating,
            Pageable pageable) {

        if (rating == null) {
            throw new BusinessRuleException(
                    "Review rating must not be null.");
        }

        return performanceReviewRepository.findByRating(
                        rating, pageable)
                .map(performanceReviewMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PerformanceReviewResponse> getReviewsByDateRange(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        if (startDate == null || endDate == null) {
            throw new BusinessRuleException(
                    "Review start date and end date are required.");
        }

        if (startDate.isAfter(endDate)) {
            throw new BusinessRuleException(
                    "Start date cannot be after end date.");
        }

        return performanceReviewRepository
                .findByReviewDateBetween(startDate, endDate, pageable)
                .map(performanceReviewMapper::toResponse);
    }

    @Override
    public PerformanceReviewResponse updateReview(
            Long id, PerformanceReviewRequest request) {

        PerformanceReview review = findReview(id);

        validateReviewDate(request.reviewDate());

        Employee employee = findEmployee(request.employeeId());

        performanceReviewMapper.updateEntity(review, request);
        review.setEmployee(employee);

        PerformanceReview updatedReview =
                performanceReviewRepository.save(review);

        logger.info("Performance review updated with ID: {}", id);

        return performanceReviewMapper.toResponse(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {

        PerformanceReview review = findReview(id);

        performanceReviewRepository.delete(review);

        logger.info("Performance review deleted with ID: {}", id);
    }

    private PerformanceReview findReview(Long id) {

        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "Review ID must be a positive number.");
        }

        return performanceReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Performance review not found with ID: " + id));
    }

    private Employee findEmployee(Long id) {

        if (id == null || id <= 0) {
            throw new BusinessRuleException(
                    "Employee ID must be a positive number.");
        }

        return employeeRepository.findByIdWithDepartment(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with ID: " + id));
    }

    private void validateReviewDate(LocalDate reviewDate) {

        if (reviewDate == null) {
            throw new BusinessRuleException(
                    "Review date must not be null.");
        }

        if (reviewDate.isAfter(LocalDate.now())) {
            throw new BusinessRuleException(
                    "Review date cannot be in the future.");
        }
    }
}