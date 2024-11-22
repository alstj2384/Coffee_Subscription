package cafeSubscription.coffee.domain.menu.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MenuUpdateDto {
    private String menuName;
    private Integer mPrice;
    private String menuInfo;

    private List<MultipartFile> imageFiles; // 추가할 이미지 파일
    private List<Long> deleteFiles;
    // TODO deleteImages, image 속성 추가
}
