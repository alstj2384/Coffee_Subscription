package cafeSubscription.coffee.domain.diary.DTO;

import lombok.Data;

@Data
public class AddDiaryRequest {
    private String title;
    private String diaryContent;
}
