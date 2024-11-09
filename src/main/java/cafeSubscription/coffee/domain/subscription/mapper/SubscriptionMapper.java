package cafeSubscription.coffee.domain.subscription.mapper;

import cafeSubscription.coffee.domain.subscription.dto.SubscriptionType;
import cafeSubscription.coffee.domain.subscription.dto.response.SubscriptionViewDto;

public class SubscriptionMapper {

    public static SubscriptionViewDto toSubscriptionViewDto(SubscriptionType type){
        return SubscriptionViewDto.builder()
                .typeId(type.getTypeId())
                .tPrice(type.getPrice())
                .duration(type.getDuration())
                .typeName(type.getTypeName())
                .build();
    }
}
