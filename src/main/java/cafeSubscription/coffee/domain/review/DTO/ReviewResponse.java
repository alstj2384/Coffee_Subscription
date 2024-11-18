package cafeSubscription.coffee.domain.review.DTO;

import cafeSubscription.coffee.domain.review.custom.Keyword;
import cafeSubscription.coffee.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReviewResponse {
    private final Long reviewId;
    private final User user;
    private final Keyword keyword;
    private final List<String> rImage;
    private final LocalDateTime createdAt;
    private final String rContent;
}
