package cafeSubscription.coffee.domain.menu.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MenuCreateDto {
    private String menuName;
    private String menuInfo;
    private Integer mPrice;
    private List<MultipartFile> imageFiles;
}
