package cafeSubscription.coffee.domain.diary.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DiaryResponse {
    private long diaryId;
    private final String title;
    private final String diaryContent;

    private final LocalDateTime entryDate;
    private final Integer view;
    //이미지 추가
}
