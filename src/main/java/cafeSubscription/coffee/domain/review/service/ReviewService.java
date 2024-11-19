package cafeSubscription.coffee.domain.review.service;

import cafeSubscription.coffee.domain.review.DTO.AddReviewRequest;
import cafeSubscription.coffee.domain.review.entity.Review;
import cafeSubscription.coffee.domain.review.repository.ReviewRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Review save(AddReviewRequest addReviewRequest) {
        return reviewRepository.save(Review.builder()
                        .rContent(addReviewRequest.getRContent())
                        .rImage(addReviewRequest.getRImage())
                        .createdAt(LocalDateTime.now())
                        .reportCount(0)
                        .build()
        );
    }

    @Transactional
    public Review update(Long reviewId, AddReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.REVIEW_NOT_FOUND.getMsg()));

        if(request.getRImage() == null || request.getRContent() == null) {
            throw new IllegalArgumentException(ErrorCode.REVIEW_UPDATE_FAILED.getMsg());
        }

        review.update(request.getRContent(), request.getKeyword(), request.getRImage());

        return reviewRepository.save(review);
    }

    @Transactional
    public void delete(Long reviewId) {
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.REVIEW_DELETION_FAILED.getMsg()));

        reviewRepository.deleteById(reviewId);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}
