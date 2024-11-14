package cafeSubscription.coffee.domain.diary.controller;

import cafeSubscription.coffee.domain.diary.DTO.AddDiaryRequest;
import cafeSubscription.coffee.domain.diary.DTO.DiaryResponse;
import cafeSubscription.coffee.domain.diary.DTO.UpdateDiaryRequest;
import cafeSubscription.coffee.domain.diary.entity.Diary;
import cafeSubscription.coffee.domain.diary.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diary")
@Tag(name = "일기 CRUD", description = "일기 작성, 삭제 ,조회 api입니다")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @Operation(summary = "카페사장 일기 추가 API", description = "카페사장님이 일기를 작성하는 api입니다")
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addDiary(@RequestBody AddDiaryRequest request) {
        return createResponseEntity(diaryService.save(request), "일기 작성 요청에 성공했습니다.", HttpStatus.CREATED);
    }

    @Operation(summary = "카페에 작성된 일기를 조회하는 api", description = "카페 인스타피드처럼 상세조회가 아닌것을 의도하기 때문에 개별조회는 구현X")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> findAllDiary() {
        List<DiaryResponse> diaryList = diaryService.findAll()
                .stream()
                .map(DiaryResponse::new)
                .toList();
        return createResponseEntity(diaryList, "일기 조회요청에 성공했습니다.", HttpStatus.OK);
    }

    @Operation(summary = "일기삭제 API", description = "카페 사장이 일기 삭제 api입니다")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Map<String, Object>> deleteDiary(@PathVariable long diaryId) {
        diaryService.delete(diaryId);
        return createResponseEntity(null, "일기 삭제가 완료되었습니다.", HttpStatus.OK);
    }

    @Operation(summary = "일기수정 API", description = "카페 사장이 일기 수정 api입니다")
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
