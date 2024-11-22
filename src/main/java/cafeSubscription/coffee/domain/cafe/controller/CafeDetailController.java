package cafeSubscription.coffee.domain.cafe.controller;


import cafeSubscription.coffee.domain.cafe.dto.CafeDetailsDTO;
import cafeSubscription.coffee.domain.cafe.service.CafeDetailService;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
@Tag(name = "카페 상세정보 조회", description = "카페아이디, 카페이름, 핀번호(쿠폰사용시 입력), 역할, 이미지 조회")
public class CafeDetailController {

    private final CafeDetailService cafeDetailService;


    @Operation(summary = "카페상세 정보 조회API", description = "카페 상세 정보(카페아이디, 이름, 핀번호, 역할, 카페 프로필)를 조회 합니다")
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
