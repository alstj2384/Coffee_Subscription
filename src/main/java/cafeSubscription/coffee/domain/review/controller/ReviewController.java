package cafeSubscription.coffee.domain.review.controller;

import cafeSubscription.coffee.domain.review.DTO.AddReviewRequest;
import cafeSubscription.coffee.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addReview(@RequestBody AddReviewRequest addReviewRequest) {
        return createResponseEntity(reviewService.save(addReviewRequest), "리뷰 작성에 성공했습니다.", HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Map<String, Object>> updateReview(@PathVariable Integer reviewId, @RequestBody AddReviewRequest request) {
        return createResponseEntity(reviewService.update(reviewId, request), "리뷰 수정이 완료되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable Integer reviewId) {
        reviewService.delete(reviewId);
        return createResponseEntity(null, "리뷰 삭제가 완료되었습니다.", HttpStatus.OK);
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
