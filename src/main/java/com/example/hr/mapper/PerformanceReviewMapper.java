
package com.example.hr.mapper;

import org.springframework.stereotype.Component;

import com.example.hr.dto.request.PerformanceReviewRequest;
import com.example.hr.dto.response.PerformanceReviewResponse;
import com.example.hr.entity.PerformanceReview;

@Component
public class PerformanceReviewMapper {

    public PerformanceReview toEntity(
            PerformanceReviewRequest request) {

        PerformanceReview review = new PerformanceReview();

        review.setReviewDate(request.reviewDate());
        review.setRating(request.rating());
        review.setComments(request.comments());
        review.setReviewerName(request.reviewerName());

        return review;
    }

    public void updateEntity(
            PerformanceReview review,
            PerformanceReviewRequest request) {

        review.setReviewDate(request.reviewDate());
        review.setRating(request.rating());
        review.setComments(request.comments());
        review.setReviewerName(request.reviewerName());
    }

    public PerformanceReviewResponse toResponse(
            PerformanceReview review) {

        return new PerformanceReviewResponse(
                review.getId(),
                review.getReviewDate(),
                review.getRating(),
                review.getComments(),
                review.getReviewerName(),
                review.getEmployee().getId(),
                review.getEmployee().getFirstName() + " "
                        + review.getEmployee().getLastName(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}

