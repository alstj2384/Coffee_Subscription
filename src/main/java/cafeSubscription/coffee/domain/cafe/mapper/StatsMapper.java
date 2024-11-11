package cafeSubscription.coffee.domain.cafe.mapper;

import cafeSubscription.coffee.domain.cafe.dto.StatsDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StatsMapper {
    public StatsDTO toStatsDTO(int totalReviewCount, int totalDiaryCount){
        return StatsDTO.builder()
                //.dailyCouponUsage(dailyCouponUsage)
                //.weeklyCouponUsage(weeklyCouponUsage)
                .totalReviewCount(totalReviewCount)
                .totalDiaryCount(totalDiaryCount)
                .build();
    }
}
