package cafeSubscription.coffee.domain.menu.dto.response;

import cafeSubscription.coffee.domain.menu.entity.Menu;
import cafeSubscription.coffee.global.file.UploadFileViewDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MenuViewDto {

    private Long menuId;
    private String menuName;

    private String menuInfo;
    private Integer mPrice;
    private List<UploadFileViewDto> images;


}
