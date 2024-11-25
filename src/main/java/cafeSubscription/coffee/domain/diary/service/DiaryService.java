package cafeSubscription.coffee.domain.diary.service;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.diary.DTO.AddDiaryRequest;
import cafeSubscription.coffee.domain.diary.DTO.UpdateDiaryRequest;
import cafeSubscription.coffee.domain.diary.entity.Diary;
import cafeSubscription.coffee.domain.diary.repositoty.DiaryRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DiaryService {
    private final CafeRepository cafeRepository;
    private final DiaryRepository diaryRepository;

    // 일기 저장
    @Transactional
    public Diary save(Long cafeId, AddDiaryRequest request){

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg()));

        Diary diary = Diary.builder()
                .title(request.getTitle())
                .diaryContent(request.getDiaryContent())
                .cafe(cafe)
                .build();
        Diary save = diaryRepository.save(diary);
        return save;
    }

    // 카페별 일기 조회
    public List<Diary> findAll(Long cafeId) {
        cafeRepository.findByCafeId(cafeId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg()));

        return diaryRepository.findAllByCafeCafeId(cafeId).orElse(null);
    }

    // 일기 하나 상세 조회(안쓸듯)
    public Diary findById(Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMsg())); // 예외처리
    }

    // 일기 수정
    @Transactional
    public Diary update(long diaryId, UpdateDiaryRequest request) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMsg())); // 예외처리

        diary.update(request.getTitle(), request.getDiaryContent());

        return diaryRepository.save(diary);
    }

    // 일기 삭제
    public Diary delete(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMsg()));
        diaryRepository.delete(diary);
        return diary;
    }
}
