package cafeSubscription.coffee.domain.cafe.controller;


import cafeSubscription.coffee.domain.cafe.dto.CafeDetailsDTO;
import cafeSubscription.coffee.domain.cafe.service.CafeDetailService;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class CafeDetailController {

    private final CafeDetailService cafeDetailService;



    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDetailsDTO> getCafeDetails(@PathVariable Long cafeId, @AuthenticationPrincipal User user) {

        Long currentUserId = user.getUserId();
        log.info("사용자 '{}'에 의한 카페 상세 정보 접근",  currentUserId);

        // 현재 사용자와 관련된 카페인지 확인하거나, 권한을 체크하는 로직 추가
        if (!cafeDetailService.isUserAuthorizedForCafe(currentUserId, cafeId)) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }

        CafeDetailsDTO cafeDetails = cafeDetailService.getCafeDetails(cafeId);
        return ResponseEntity.ok(cafeDetails);
    }
}
