package cafeSubscription.coffee.domain.subscription.dto.response;

import cafeSubscription.coffee.domain.subscription.dto.SubscriptionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionViewDto {
    private final Long typeId;
    private final String typeName;
    private final Integer duration;
    private final Integer tPrice;

}
