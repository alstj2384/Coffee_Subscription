package cafeSubscription.coffee.domain.diary.mapper;

import cafeSubscription.coffee.domain.diary.DTO.DiaryResponse;
import cafeSubscription.coffee.domain.diary.entity.Diary;

public class DiaryMapper {
    public static DiaryResponse toDiaryResponse(Diary diary){
        return DiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .title(diary.getTitle())
                .diaryContent(diary.getDiaryContent())
                .entryDate(diary.getEntryDate())
                .view(diary.getView())
                .build();
    }
}
