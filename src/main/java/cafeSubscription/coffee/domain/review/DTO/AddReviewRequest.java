package cafeSubscription.coffee.domain.review.DTO;

import cafeSubscription.coffee.domain.review.entity.Review;
import cafeSubscription.coffee.domain.review.custom.Keyword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddReviewRequest {
    private String rContent; // 리뷰 내용
    private Enum<Keyword> keyword;
    private List<String> rImage;

    public Review toEntity() {
        return Review.builder()
                .rContent(rContent)
                .keyword((Keyword) keyword)
                .rImage(rImage)
                .build();
    }
}
