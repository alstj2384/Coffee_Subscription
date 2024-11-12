package cafeSubscription.coffee.domain.cafe.mapper;

import cafeSubscription.coffee.domain.cafe.DTO.StatsDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StatsMapper {
    public StatsDTO toStatsDTO(int totalReviewCount, int totalDiaryCount){
        return StatsDTO.builder()
                .totalReviewCount(totalReviewCount)
                .totalDiaryCount(totalDiaryCount)
                .build();
    }
}
