package cafeSubscription.coffee.domain.review.controller;

import cafeSubscription.coffee.domain.review.DTO.AddReviewRequest;
import cafeSubscription.coffee.domain.review.DTO.ReviewResponse;
import cafeSubscription.coffee.domain.review.entity.Review;
import cafeSubscription.coffee.domain.review.mapper.ReviewMapper;
import cafeSubscription.coffee.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

//    @PreAuthorize("hasRole('customer')")
    @PostMapping("/add")
    public ResponseEntity<ReviewResponse> addReview(//@AuthenticationPrincipal User user,
          @RequestBody AddReviewRequest addReviewRequest) {
//        String username = user.getUsername();
        Review review = reviewService.save(addReviewRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewMapper.toReviewResponse(review));
        // "리뷰 작성에 성공했습니다."
    }

//    @PreAuthorize("hasRole('customer')")
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(//@AuthenticationPrincipal User user,
            @PathVariable Long reviewId, AddReviewRequest addReviewRequest) {

        Review review = reviewService.update(reviewId, addReviewRequest);

        return ResponseEntity.status(HttpStatus.OK).body(ReviewMapper.toReviewResponse(review));
        // "리뷰 수정이 완료되었습니다."
    }

//    @PreAuthorize("hasRole('customer')")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> deleteReview(//@AuthenticationPrincipal User user,
            @PathVariable Long reviewId) {
        reviewService.delete(reviewId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
        // "리뷰 삭제가 완료되었습니다."
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReviewResponse>> findAllReview() {
        List<Review> reviewList = reviewService.findAll();

        List<ReviewResponse> response = reviewList
                .stream()
                .map(ReviewMapper::toReviewResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
        // "리뷰 조회요청에 성공했습니다."
    }
}
