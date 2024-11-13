package cafeSubscription.coffee.domain.review.DTO;

import cafeSubscription.coffee.domain.review.entity.Review;
import cafeSubscription.coffee.domain.review.custom.Keyword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AddReviewRequest {
    @JsonProperty("rContent")
    private String rContent; // 리뷰 내용
//    private Enum<Keyword> keyword;
    @JsonProperty("rImage")
    private List<String> rImage;

    public Review toEntity() {
        return Review.builder()
                .rContent(rContent)
//                .keyword((Keyword) keyword)
                .rImage(rImage)
                .reportCount(0)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
