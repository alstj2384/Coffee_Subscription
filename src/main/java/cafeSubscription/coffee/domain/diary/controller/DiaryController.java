package cafeSubscription.coffee.domain.diary.controller;

import cafeSubscription.coffee.domain.diary.DTO.AddDiaryRequest;
import cafeSubscription.coffee.domain.diary.DTO.DiaryResponse;
import cafeSubscription.coffee.domain.diary.DTO.UpdateDiaryRequest;
import cafeSubscription.coffee.domain.diary.entity.Diary;
import cafeSubscription.coffee.domain.diary.mapper.DiaryMapper;
import cafeSubscription.coffee.domain.diary.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
@Slf4j
@Tag(name = "일기 CRUD", description = "일기 작성, 삭제 ,조회 api입니다")
public class DiaryController {

    private final DiaryService diaryService;

    @PreAuthorize("hasRole('owner')")
    @Operation(summary = "카페사장 일기 추가 API", description = "카페사장님이 일기를 작성하는 api입니다")
    @PostMapping("/{cafeId}/add")
        public ResponseEntity<DiaryResponse> addDiary(@AuthenticationPrincipal User user, @PathVariable Long cafeId,
                                                      @RequestBody AddDiaryRequest request) {

            // 사용자 인증 확인 (null 체크)
            if (user == null) {
                throw new RuntimeException("사용자 인증이 필요합니다.");
            }

            // 인증된 사용자의 userName 얻기
            String username = user.getUsername(); // 사용자 userName이 받아짐
            log.info("인증된 유저ID: {}", username);

            Diary diary = diaryService.save(cafeId, request);
            DiaryResponse response = DiaryMapper.toDiaryResponse(diary);

            log.info("카페ID: {} 일기 생성", cafeId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

    @Operation(summary = "일기 조회 API", description = "일기 조회 api입니다")
    @GetMapping("/{cafeId}")
    public ResponseEntity<?> list(@PathVariable Long cafeId){
        List<Diary> list = diaryService.findAll(cafeId);

        List<DiaryResponse> response = list.stream().map(DiaryMapper::toDiaryResponse).toList();

        log.info("카페ID: {} 일기 전체 조회", cafeId);
        return ResponseEntity.ok().body(response);
    }

    @PreAuthorize("hasRole('owner')") // 사장권한
    @Operation(summary = "일기삭제 API", description = "카페 사장이 일기 삭제 api입니다")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> deleteDiary(@AuthenticationPrincipal User user, @PathVariable Long diaryId) {

        // 사용자 인증 확인 (null 체크)
        if (user == null) {
            throw new RuntimeException("사용자 인증이 필요합니다.");
        }

        // 인증된 사용자의 userName 얻기
        String username = user.getUsername(); // 사용자 userName이 받아짐
        log.info("인증된 유저ID: {}", username);

        Diary delete = diaryService.delete(diaryId);
        DiaryResponse response = DiaryMapper.toDiaryResponse(delete);

        log.info("일기ID: {} 일기 삭제", diaryId);
        return ResponseEntity.ok().body(response);
    }

    @PreAuthorize("hasRole('owner')") // 사장권한
    @Operation(summary = "일기수정 API", description = "카페 사장이 일기 수정 api입니다")
    @PatchMapping("/{diaryId}")
    public ResponseEntity<DiaryResponse> updateDiary(@AuthenticationPrincipal User user, @PathVariable Long diaryId,
                                                     @RequestBody UpdateDiaryRequest request) {

        // 사용자 인증 확인 (null 체크)
        if (user == null) {
            throw new RuntimeException("사용자 인증이 필요합니다.");
        }

        // 인증된 사용자의 userName 얻기
        String username = user.getUsername(); // 사용자 userName이 받아짐
        log.info("인증된 유저ID: {}", username);

        Diary update = diaryService.update(diaryId, request);
        DiaryResponse response = DiaryMapper.toDiaryResponse(update);

        log.info("일기ID: {} 일기 수정", diaryId);
        return ResponseEntity.ok().body(response);
    }
}
