package cafeSubscription.coffee.domain.coupon.controller;

import cafeSubscription.coffee.domain.coupon.mapper.CouponMapper;
import cafeSubscription.coffee.domain.coupon.service.CouponService;
import cafeSubscription.coffee.domain.coupon.dto.request.CouponRequest;
import cafeSubscription.coffee.domain.coupon.dto.response.CouponResponse;
import cafeSubscription.coffee.domain.coupon.entity.Coupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;
    @PostMapping("/use/{userId}")
    public ResponseEntity<CouponResponse> useCoupon(@PathVariable Long userId, @RequestBody CouponRequest couponRequest){
        // 유저 검증
        // userId로 넘기는 거 시큐리티 붙이고 나서 수정해야함
        log.info("[UseCoupon] Requested.. ");

        Coupon coupon = couponService.useCoupon(userId, couponRequest);

        CouponResponse dto = CouponMapper.toCouponResponse(coupon);

        log.info("[UseCoupon] OK!");
        return ResponseEntity.ok().body(dto);
    }
}
