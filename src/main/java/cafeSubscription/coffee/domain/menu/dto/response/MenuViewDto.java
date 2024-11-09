package cafeSubscription.coffee.domain.menu.dto.response;

import cafeSubscription.coffee.domain.menu.entity.Menu;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuViewDto {

    // TODO 이미지 기능 추가 시 이미지도 반환값으로 추가
    private Long menuId;
    private String menuName;

    private String menuInfo;
    private Integer mPrice;


}
