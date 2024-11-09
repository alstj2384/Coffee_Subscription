package cafeSubscription.coffee.domain.review.custom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Keyword {
    TASTE_GOOD("TASTE_GOOD", "맛있어요"),
    TASTE_BAD("TASTE_BAD", "별로예요"),
    SERVICE_GOOD("SERVICE_GOOD", "친절해요"),
    SERVICE_BAD("SERVICE_BAD", "불친절해요"),
    CLEAN("CLEAN", "매장이 청결해요"),
    DIRTY("DIRTY", "위생상태가 별로예요");

    private final String name;

    @JsonValue
    private final String value;

    // Enum Validation 을 위한 코드, enum 에 속하지 않으면 null 리턴
    @JsonCreator
    public static Keyword fromEventStatus(String val) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(val))
                .findAny()
                .orElse(null);
    }
}
