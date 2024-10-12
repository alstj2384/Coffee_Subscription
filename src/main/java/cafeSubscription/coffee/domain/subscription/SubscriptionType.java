package cafeSubscription.coffee.domain.subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubscriptionType {
    DAY("day", 1, 5000), WEEK("week", 7, 35000),
    MONTH("month", 30, 150000);

    private final String typeName;
    private final Integer duration;
    private final Integer price;
}
