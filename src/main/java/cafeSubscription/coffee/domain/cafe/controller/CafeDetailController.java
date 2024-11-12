package cafeSubscription.coffee.domain.cafe.controller;


import cafeSubscription.coffee.domain.cafe.dto.CafeDetailsDTO;
import cafeSubscription.coffee.domain.cafe.service.CafeDetailService;
import cafeSubscription.coffee.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class CafeDetailController {

    private final CafeDetailService cafeDetailService;

    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDetailsDTO> getCafeDetails(@PathVariable Long cafeId, @AuthenticationPrincipal User user) {

        String currentUserEmail = user.getUsername();
        log.info("사용자 '{}'에 의한 카페 상세 정보 접근", currentUserEmail);

        // 현재 사용자와 관련된 카페인지 확인하거나, 권한을 체크하는 로직 추가
        if (!cafeDetailService.isUserAuthorizedForCafe(currentUserEmail, cafeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 권한이 없으면 403 오류 반환
        }

        CafeDetailsDTO cafeDetails = cafeDetailService.getCafeDetails(cafeId);
        return ResponseEntity.ok(cafeDetails);
    }
}
