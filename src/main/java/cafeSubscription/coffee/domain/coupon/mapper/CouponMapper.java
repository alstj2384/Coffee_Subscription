package cafeSubscription.coffee.domain.coupon.mapper;

import cafeSubscription.coffee.domain.coupon.dto.response.CouponResponse;
import cafeSubscription.coffee.domain.coupon.entity.Coupon;

public class CouponMapper {

    public static CouponResponse toCouponResponse(Coupon coupon){
        return CouponResponse.builder()
                .cafeId(coupon.getCafe().getCafeId())
                .userId(coupon.getUser().getUserId())
                .build();
    }
}
