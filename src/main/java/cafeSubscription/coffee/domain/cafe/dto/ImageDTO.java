package cafeSubscription.coffee.domain.cafe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ImageDTO {
    private Long imageId;
    private String imagePath;
}
