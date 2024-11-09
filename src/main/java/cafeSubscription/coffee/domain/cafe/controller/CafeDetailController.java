package cafeSubscription.coffee.domain.cafe.controller;


import cafeSubscription.coffee.domain.cafe.dto.CafeDetailsDTO;
import cafeSubscription.coffee.domain.cafe.service.CafeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class CafeDetailController {

    private final CafeDetailService cafeDetailService;

    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDetailsDTO> getCafeDetails(@PathVariable Long cafeId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName(); // 이메일을 사용자 식별자로 가정
        log.info("사용자 '{}'에 의한 카페 상세 정보 접근", currentUserEmail);

        CafeDetailsDTO cafeDetails = cafeDetailService.getCafeDetails(cafeId);
        return ResponseEntity.ok(cafeDetails);
    }
}
