package cafeSubscription.coffee.domain.coupon.controller;

import cafeSubscription.coffee.domain.coupon.mapper.CouponMapper;
import cafeSubscription.coffee.domain.coupon.service.CouponService;
import cafeSubscription.coffee.domain.coupon.dto.request.CouponRequest;
import cafeSubscription.coffee.domain.coupon.dto.response.CouponResponse;
import cafeSubscription.coffee.domain.coupon.entity.Coupon;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer 쿠폰 사용", description = "손님이 카페에서 쿠폰 사용 api")
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;
    private final RegisterRepository registerRepository;

    @Operation(summary = "카페에서 쿠폰 사용 API", description = "customer가 카페에서 쿠폰을 사용하는 api")
    @PostMapping("/use")
    public ResponseEntity<CouponResponse> useCoupon(@AuthenticationPrincipal User user, @RequestBody CouponRequest couponRequest){
        log.info("[UseCoupon] Requested.. ");

        cafeSubscription.coffee.domain.user.entity.User user1 = registerRepository.findByUsername(user.getUsername()).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NON_EXISTENT_USER.getMsg()));
        Long userId = user1.getUserId();

        Coupon coupon = couponService.useCoupon(userId, couponRequest);

        CouponResponse dto = CouponMapper.toCouponResponse(coupon);

        log.info("[UseCoupon] OK!");
        return ResponseEntity.ok().body(dto);
    }
}
