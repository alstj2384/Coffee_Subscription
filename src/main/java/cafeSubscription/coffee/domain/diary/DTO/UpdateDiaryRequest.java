package cafeSubscription.coffee.domain.diary.DTO;

import cafeSubscription.coffee.domain.image.entity.Image;
import lombok.Data;

@Data
public class UpdateDiaryRequest {
    private String title;
    private String diaryContent;
}