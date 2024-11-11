package cafeSubscription.coffee.domain.cafe.controller;


import cafeSubscription.coffee.domain.cafe.DTO.StatsDTO;
import cafeSubscription.coffee.domain.cafe.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/summary") //리뷰수, 일기수 조회
    public ResponseEntity<StatsDTO> getSummary(@RequestParam Long cafeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerEmail = authentication.getName();

        StatsDTO stats = statsService.getSummary(cafeId, ownerEmail);
        return ResponseEntity.ok().body(stats);

    }

    @GetMapping("/period/usage")
    public ResponseEntity<Long> getPeriodUsage(@RequestParam Long cafeId,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerEmail = authentication.getName();
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        long periodUsage = statsService.getPeriodCouponUsage(cafeId, ownerEmail, startDateTime, endDateTime);
        return ResponseEntity.ok(periodUsage);
    }
}
