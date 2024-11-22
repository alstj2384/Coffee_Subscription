package cafeSubscription.coffee.domain.cafe.dto;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CafeResponseDto {

    private Long cafeId;
    private String cafeName;
    private String description;
    private OperatingHour operatingHour;

    public static CafeResponseDto toDto(Cafe cafe){
        return CafeResponseDto.builder()
                .cafeId(cafe.getCafeId())
                .cafeName(cafe.getCafeName())
                .description(cafe.getDescription())
                .operatingHour(cafe.getOperatingHour())
                .build();
    }

}
