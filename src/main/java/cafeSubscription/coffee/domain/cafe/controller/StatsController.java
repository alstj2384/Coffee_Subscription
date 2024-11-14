package cafeSubscription.coffee.domain.cafe.controller;


import cafeSubscription.coffee.domain.cafe.dto.StatsDTO;
import cafeSubscription.coffee.domain.cafe.service.StatsService;
import cafeSubscription.coffee.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(name = "카페 통계 조회", description = "카페 통계 (리뷰수, 일기수, 쿠폰사용량) 조회합니다")
public class StatsController {

    private final StatsService statsService;

    @Operation(summary = "카페 리뷰, 일기수 조회 API", description = "카페 리뷰, 일기 수를 조회합니다")
    @GetMapping("/summary") //리뷰수, 일기수 조회
    public ResponseEntity<StatsDTO> getSummary(@RequestParam Long cafeId, @AuthenticationPrincipal User user ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerEmail = authentication.getName();

        StatsDTO stats = statsService.getSummary(cafeId, ownerEmail);
        return ResponseEntity.ok().body(stats);

    }

    @Operation(summary = "카페에서 사용된 쿠폰 사용량 조회 API", description = "시작날짜와 마지막날짜 선택시 해당 기간동안 사용량을 조회합니다")
    @GetMapping("/period/usage")
    public ResponseEntity<Long> getPeriodUsage(@RequestParam Long cafeId,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                               @AuthenticationPrincipal User user) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerEmail = authentication.getName();
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        long periodUsage = statsService.getPeriodCouponUsage(cafeId, ownerEmail, startDateTime, endDateTime);
        return ResponseEntity.ok(periodUsage);
    }
}
