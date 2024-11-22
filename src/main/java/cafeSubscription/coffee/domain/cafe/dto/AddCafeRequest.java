package cafeSubscription.coffee.domain.cafe.dto;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.image.DTO.AddImageRequest;
import cafeSubscription.coffee.domain.image.entity.Image;
import cafeSubscription.coffee.domain.operatingHour.OperatingHour;
import cafeSubscription.coffee.domain.user.entity.Business;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCafeRequest {
    private String cafeName;
    private String description;
    private Integer pin;
    private String location;
    private OperatingHour operatingHour; // OperatingHour 객체를 직접 받음

    // toEntity 메서드에서 OperatingHour를 Cafe에 연결하여 반환
    public Cafe toEntity(OperatingHour operatingHour, Business business) {
        return Cafe.builder()
                .cafeName(cafeName)
                .description(description)
                .pin(pin)
                .location(location)
                .operatingHour(operatingHour) // OperatingHour 연결
                .business(business)
                .build();
    }

}
