package cafeSubscription.coffee.domain.diary.service;

import cafeSubscription.coffee.domain.diary.DTO.AddDiaryRequest;
import cafeSubscription.coffee.domain.diary.DTO.UpdateDiaryRequest;
import cafeSubscription.coffee.domain.diary.Diary;
import cafeSubscription.coffee.domain.diary.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    // 일기 저장
    @Transactional
    public Diary save(AddDiaryRequest request) {
        return diaryRepository.save(request.toEntity());
    }

    // 일기 전체 조회
    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }

    // 일기 하나 상세 조회(사장별로 구별해야 하지 않나..)
    public Diary findById(Integer diaryId) {
        return diaryRepository.findById(diaryId)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + diaryId)); // 예외처리
    }

    // 일기 삭제
    public void delete(Integer diaryId) {
        diaryRepository.deleteById(diaryId);
    }

    // 일기 수정
    @Transactional
    public Diary update(Integer diaryId, UpdateDiaryRequest request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + diaryId)); // 예외처리

        diary.update(request.getTitle(), request.getDiaryContent());

        return diaryRepository.save(diary);
    }
}
