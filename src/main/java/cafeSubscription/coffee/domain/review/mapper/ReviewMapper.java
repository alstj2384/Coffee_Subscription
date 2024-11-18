package cafeSubscription.coffee.domain.review.mapper;


import cafeSubscription.coffee.domain.review.DTO.ReviewResponse;
import cafeSubscription.coffee.domain.review.entity.Review;

public class ReviewMapper {
    public static ReviewResponse toReviewResponse(Review review){
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .rContent(review.getRContent())
                .rImage(review.getRImage())
                .keyword(review.getKeyword())
                .createdAt(review.getCreatedAt())
                .build();
    }
}