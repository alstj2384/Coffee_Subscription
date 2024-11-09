package cafeSubscription.coffee.domain.menu.dto.request;

import lombok.Data;

@Data
public class MenuUpdateDto {
    private String menuName;
    private Integer mPrice;
    private String menuInfo;

    // TODO deleteImages, image 속성 추가
}
