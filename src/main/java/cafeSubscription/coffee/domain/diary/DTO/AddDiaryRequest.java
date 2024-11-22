package cafeSubscription.coffee.domain.diary.DTO;

import cafeSubscription.coffee.domain.image.DTO.AddImageRequest;
import cafeSubscription.coffee.domain.image.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class AddDiaryRequest {
    private String title;
    private String diaryContent;
}
