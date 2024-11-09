package cafeSubscription.coffee.domain.menu.mapper;

import cafeSubscription.coffee.domain.menu.dto.response.MenuViewDto;
import cafeSubscription.coffee.domain.menu.entity.Menu;

public class MenuMapper {
    public static MenuViewDto toMenuViewDto(Menu menu){
        return MenuViewDto.builder()
                .menuId(menu.getId())
                .menuInfo(menu.getMenuInfo())
                .menuName(menu.getMenuName())
                .mPrice(menu.getMPrice())
                .build();
    }
}
