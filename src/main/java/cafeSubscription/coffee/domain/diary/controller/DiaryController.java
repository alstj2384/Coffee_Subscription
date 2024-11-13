package cafeSubscription.coffee.domain.diary.controller;

import cafeSubscription.coffee.domain.diary.DTO.AddDiaryRequest;
import cafeSubscription.coffee.domain.diary.DTO.DiaryResponse;
import cafeSubscription.coffee.domain.diary.DTO.UpdateDiaryRequest;
import cafeSubscription.coffee.domain.diary.entity.Diary;
import cafeSubscription.coffee.domain.diary.mapper.DiaryMapper;
import cafeSubscription.coffee.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;

    //TODO - 사장권한
    @PostMapping("/{cafeId}/add")
        public ResponseEntity<DiaryResponse> addDiary(@PathVariable Long cafeId,
                                                      @RequestBody AddDiaryRequest request) {

            //TODO - cafeId 검증

            Diary diary = diaryService.save(cafeId, request);
            DiaryResponse response = DiaryMapper.toDiaryResponse(diary);

            log.info("{} 카페 일기 생성", cafeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }


    @GetMapping("/{cafeId}")
    public ResponseEntity<?> list(@PathVariable Long cafeId){
        List<Diary> list = diaryService.findAll(cafeId);

        List<DiaryResponse> response = list.stream().map(DiaryMapper::toDiaryResponse).toList();

        log.info("카페ID: {} 일기 전체 조회", cafeId);
        return ResponseEntity.ok().body(response);
    }


    //TODO - 사장권한
    @PatchMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> updateDiary(@PathVariable Long diaryId,
                                                     @RequestBody UpdateDiaryRequest request) {

        //TODO - cafeId 검증
        Diary update = diaryService.update(diaryId, request);
        DiaryResponse response = DiaryMapper.toDiaryResponse(update);

        log.info("일기ID: {} 일기 수정", diaryId);
        return ResponseEntity.ok().body(response);
    }

    //TODO - 사장권한
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> deleteDiary(@PathVariable Long diaryId) {

        //TODO - cafeId 검증
        Diary delete = diaryService.delete(diaryId);
        DiaryResponse response = DiaryMapper.toDiaryResponse(delete);

        log.info("일기ID: {} 일기 삭제", diaryId);
        return ResponseEntity.ok().body(response);
    }
}
