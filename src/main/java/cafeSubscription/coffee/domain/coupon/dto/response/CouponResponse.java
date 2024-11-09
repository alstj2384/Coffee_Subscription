package cafeSubscription.coffee.domain.coupon.dto.response;

import cafeSubscription.coffee.domain.coupon.entity.Coupon;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponResponse {
    private Long userId;
    private Long cafeId;

}
