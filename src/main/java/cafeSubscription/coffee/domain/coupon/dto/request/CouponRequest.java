package cafeSubscription.coffee.domain.coupon.dto.request;

import lombok.Data;

@Data
public class CouponRequest {
    private Long cafeId;
    private Integer pin;
}
