package cafeSubscription.coffee.domain.cafe.DTO;

import cafeSubscription.coffee.domain.cafe.Cafe;
import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCafeRequest {
    private String cafeName;
    private String description;
    private String location;
    private OperatingHour operatingHour;

    // toEntity 메서드에서 OperatingHour를 Cafe에 연결하여 반환
    public Cafe toEntity(OperatingHour operatingHour) {
        return Cafe.builder()
                .cafeName(cafeName)
                .description(description)
                .location(location)
                .operatingHour(operatingHour) // OperatingHour 연결
                .build();
    }
}
