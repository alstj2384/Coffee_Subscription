package cafeSubscription.coffee.domain.diary.DTO;

import lombok.Data;

@Data
public class UpdateDiaryRequest {
    private String title;
    private String diaryContent;
}