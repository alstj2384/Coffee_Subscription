package cafeSubscription.coffee.domain.review.DTO;

import cafeSubscription.coffee.domain.review.custom.Keyword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddReviewRequest {
    @JsonProperty("rContent")
    private String rContent; // 리뷰 내용
    @JsonProperty("keyword")
    private Keyword keyword;
    @JsonProperty("rImage")
    private List<String> rImage;
}
