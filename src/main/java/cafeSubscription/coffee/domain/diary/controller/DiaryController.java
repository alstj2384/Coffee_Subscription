package cafeSubscription.coffee.domain.diary.controller;

import cafeSubscription.coffee.domain.diary.DTO.AddDiaryRequest;
import cafeSubscription.coffee.domain.diary.DTO.DiaryResponse;
import cafeSubscription.coffee.domain.diary.DTO.UpdateDiaryRequest;
import cafeSubscription.coffee.domain.diary.entity.Diary;
import cafeSubscription.coffee.domain.diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addDiary(@RequestBody AddDiaryRequest request) {
        return createResponseEntity(diaryService.save(request), "일기 작성 요청에 성공했습니다.", HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> findAllDiary() {
        List<DiaryResponse> diaryList = diaryService.findAll()
                .stream()
                .map(DiaryResponse::new)
                .toList();
        return createResponseEntity(diaryList, "일기 조회요청에 성공했습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Map<String, Object>> deleteDiary(@PathVariable long diaryId) {
        diaryService.delete(diaryId);
        return createResponseEntity(null, "일기 삭제가 완료되었습니다.", HttpStatus.OK);
    }

    @PutMapping("/{diaryId}")
    public ResponseEntity<Map<String, Object>> updateDiary(@PathVariable long diaryId,
                                                           @RequestBody UpdateDiaryRequest request) {
        Diary updatedDiary = diaryService.update(diaryId, request);
        return createResponseEntity(updatedDiary, "일기 수정이 완료되었습니다.", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> createResponseEntity(Object data, String message, HttpStatus status) { //요청값 확인용
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", status);
        response.put("timeStamp", LocalDateTime.now());
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }
}
