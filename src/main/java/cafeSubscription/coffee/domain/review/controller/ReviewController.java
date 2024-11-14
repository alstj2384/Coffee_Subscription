package cafeSubscription.coffee.domain.review.controller;

import cafeSubscription.coffee.domain.review.DTO.AddReviewRequest;
import cafeSubscription.coffee.domain.review.DTO.ReviewResponse;
import cafeSubscription.coffee.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "리뷰CRUD API", description = "카페 리뷰 조회, 삭제, 수정, 작성 api입니다")
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 추가 API")
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addReview(AddReviewRequest addReviewRequest) {
        return createResponseEntity(reviewService.save(addReviewRequest), "리뷰 작성에 성공했습니다.", HttpStatus.CREATED);
    }

    @Operation(summary = "리뷰 수정 API")
    @PutMapping("/{reviewId}")
    public ResponseEntity<Map<String, Object>> updateReview(@PathVariable Long reviewId, AddReviewRequest request) {
        return createResponseEntity(reviewService.update(reviewId, request), "리뷰 수정이 완료되었습니다.", HttpStatus.OK);
    }

    @Operation(summary = "리뷰 삭제 API")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
        return createResponseEntity(null, "리뷰 삭제가 완료되었습니다.", HttpStatus.OK);
    }

    @Operation(summary = "리뷰 조회 API")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> findAllReview() {
        List<ReviewResponse> reviewList = reviewService.findAll()
                .stream()
                .map(ReviewResponse::new)
                .toList();
        return createResponseEntity(reviewList, "리뷰 조회요청에 성공했습니다.", HttpStatus.OK);
    }

    ResponseEntity<Map<String, Object>> createResponseEntity(Object data, String message, HttpStatus status) { //요청값 확인용
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", status);
        response.put("timeStamp", LocalDateTime.now());
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }
}
