package cafeSubscription.coffee.domain.subscription.dto;

import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubscriptionType {
    DAY(0L, "day", 1, 5000),
    WEEK(1L, "week", 7, 35000),
    MONTH(2L, "month", 30, 150000);

    private final Long typeId;
    private final String typeName;
    private final Integer duration;
    private final Integer price;

    public static SubscriptionType findByTypeId(long typeId){
        for (SubscriptionType type : SubscriptionType.values()){
            if(type.getTypeId() == typeId){
                return type;
            }
        }
        throw new IllegalArgumentException(ErrorCode.SUBSCRIPTION_NOT_FOUND.getMsg());
    }
}
