package cafeSubscription.coffee.domain.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CafeDetailsDTO {
    private Integer cafeId;
    private String cafeName;
    private String role;
    private Integer pin;
    private ImageDTO image;

}
