package cafeSubscription.coffee.domain.custom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ImageType {
    PNG("PNG", "PNG"),
    JPG("JPG", "JPG");

    private final String name;

    @JsonValue
    private final String value;

    // Enum Validation 을 위한 코드, enum 에 속하지 않으면 null 리턴
    @JsonCreator
    public static ImageType fromEventStatus(String val) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(val))
                .findAny()
                .orElse(null);
    }
}