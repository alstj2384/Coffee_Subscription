package cafeSubscription.coffee.domain.diary.DTO;

import cafeSubscription.coffee.domain.diary.entity.Diary;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DiaryResponse {
    private long diaryId;
    private final String title;
    private final String diaryContent;
    private List<String> thumbnail;
    private final LocalDateTime entryDate;
    private final LocalDateTime updatedAt;
    private final Integer view;

    public DiaryResponse(Diary diary){
        this.diaryId = diary.getDiaryId();
        this.title = diary.getTitle();
        this.diaryContent = diary.getDiaryContent();
        this.thumbnail = diary.getThumbnail();
        this.entryDate = diary.getEntryDate();
        this.updatedAt = diary.getUpdatedAt();
        this.view = diary.getView();
    }
}
