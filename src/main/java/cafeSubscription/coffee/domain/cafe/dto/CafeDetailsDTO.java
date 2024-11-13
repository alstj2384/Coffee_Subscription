package cafeSubscription.coffee.domain.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CafeDetailsDTO {
    private Long cafeId;
    private String cafeName;
    private String role;
    private Integer pin;
    private cafeSubscription.coffee.domain.cafe.dto.ImageDTO image;

}
