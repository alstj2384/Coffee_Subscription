package cafeSubscription.coffee.domain.diary.DTO;

import cafeSubscription.coffee.domain.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateDiaryRequest {

    private String title;
    private String diaryContent;

    public Diary toEntity() {
        return Diary.builder()
                .title(title)
                .diaryContent(diaryContent)
                .build();
    }
}