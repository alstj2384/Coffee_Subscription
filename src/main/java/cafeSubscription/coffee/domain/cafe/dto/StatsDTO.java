package cafeSubscription.coffee.domain.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StatsDTO {
    private int dailyCouponUsage;
    private int weeklyCouponUsage;
    private int totalReviewCount;
    private int totalDiaryCount;
}
