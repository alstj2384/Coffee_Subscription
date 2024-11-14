package cafeSubscription.coffee.domain.review.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddReviewRequest {
    @JsonProperty("rContent")
    private String rContent; // 리뷰 내용
//    private Enum<Keyword> keyword;
    @JsonProperty("rImage")
    private List<String> rImage;
}
