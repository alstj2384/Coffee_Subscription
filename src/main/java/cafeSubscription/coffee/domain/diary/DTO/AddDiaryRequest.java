package cafeSubscription.coffee.domain.diary.DTO;


import cafeSubscription.coffee.domain.diary.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDiaryRequest {
    private String title;
    private String diaryContent;
    private List<String> thumbnail; // 최대 5개의 이미지 경로를 담을 수 있는 리스트 추가

    public Diary toEntity(){
        return  Diary.builder()
                .title(title)
                .diaryContent(diaryContent)
                .thumbnail(thumbnail)
                .build();
    }
}
