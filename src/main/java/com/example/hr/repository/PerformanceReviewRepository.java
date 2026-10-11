
package com.example.hr.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hr.entity.PerformanceReview;
import com.example.hr.enums.ReviewRating;

public interface PerformanceReviewRepository
        extends JpaRepository<PerformanceReview, Long> {

    Page<PerformanceReview> findByEmployeeId(
            Long employeeId, Pageable pageable);

    Page<PerformanceReview> findByRating(
            ReviewRating rating, Pageable pageable);

    Page<PerformanceReview> findByReviewDateBetween(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable);

    @Query("""
        SELECT pr FROM PerformanceReview pr
        WHERE pr.employee.id = :employeeId
        ORDER BY pr.reviewDate DESC
        """)
    Page<PerformanceReview> findEmployeeReviews(
            @Param("employeeId") Long employeeId,
            Pageable pageable);

    @Query("""
        SELECT COUNT(pr) FROM PerformanceReview pr
        WHERE pr.employee.id = :employeeId
          AND pr.rating = :rating
        """)
    long countByEmployeeAndRating(
            @Param("employeeId") Long employeeId,
            @Param("rating") ReviewRating rating);

    @Query(value = """
        SELECT COUNT(*)
        FROM performance_reviews
        WHERE rating = :rating
        """, nativeQuery = true)
    long countByRatingNative(@Param("rating") String rating);
}

