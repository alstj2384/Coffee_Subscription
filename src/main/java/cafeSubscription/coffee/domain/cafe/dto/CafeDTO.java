package cafeSubscription.coffee.domain.cafe.dto;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CafeDTO {
    private Long cafeId; // 카페 ID
    private String cafeName; // 카페 이름
    private String description; // 설명
    private String location; // 위치

    // 엔티티를 DTO로 변환하는 메서드
    public static CafeDTO fromEntity(Cafe cafe) {
        return new CafeDTO(
                cafe.getCafeId(),
                cafe.getCafeName(),
                cafe.getDescription(),
                cafe.getLocation()
        );
    }
}