package cafeSubscription.coffee.domain.review.DTO;

import cafeSubscription.coffee.domain.review.Review;
import cafeSubscription.coffee.domain.review.custom.Keyword;
import cafeSubscription.coffee.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponse {
    private final Long reviewId;
    private final User user;
    private final Keyword keyword;
    private final List<String> rImage;
    private final LocalDateTime createdAt;
    private final String rContent;

    public ReviewResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.user = review.getUser();
        this.keyword = review.getKeyword();
        this.rImage = review.getRImage();
        this.createdAt = review.getCreatedAt();
        this.rContent = review.getRContent();
    }
}
