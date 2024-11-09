package cafeSubscription.coffee.domain.coupon.service;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.coupon.dto.request.CouponRequest;
import cafeSubscription.coffee.domain.coupon.entity.Coupon;
import cafeSubscription.coffee.domain.coupon.repository.CouponRepository;
import cafeSubscription.coffee.domain.subscription.entity.Subscription;
import cafeSubscription.coffee.domain.subscription.repository.SubscriptionRepository;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {
    // 쿠폰 사용
    // 사용자가 구독중인지 확인
    // 사용날짜가 오늘이 아니면 사용가능하게끔
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final CafeRepository cafeRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public Coupon useCoupon(Long userId, CouponRequest couponRequest){
        log.info("[UseCoupon] Requested...");

        // 유저 확인
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.NON_EXISTENT_USER.getMsg()));

        Cafe cafe = cafeRepository.findById(couponRequest.getCafeId()).orElseThrow(()->
                new IllegalArgumentException(ErrorCode.CAFE_NOT_FOUND.getMsg()));


        // 쿠폰 번호가 일치하지 않을 경
        if(!cafe.getPin().equals(couponRequest.getPin()))
            throw new IllegalArgumentException(ErrorCode.COUPON_PIN_NOT_MATCH.getMsg());

        // 구독
        List<Subscription> subscriptionList = subscriptionRepository.findAllByUserUserIdOrderByStartDateDesc(userId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.SUBSCRIPTION_NOT_FOUND.getMsg()));

        Subscription subscription = subscriptionList.get(0);
        if(!subscription.isValid())
            throw new IllegalArgumentException(ErrorCode.SUBSCRIPTION_EXPIRED.getMsg());

        log.info("[useCoupon] Valid Coupon2!");

        if(subscription.isUsedToday()){
           throw new IllegalArgumentException(ErrorCode.COUPON_ALREADY_IN_USE.getMsg());
        }


        subscription.useCoupon();
        log.info("[useCoupon] Coupon2 Used Completely!");


        Coupon coupon = Coupon.builder()
                .cafe(cafe)
                .user(user)
                .usedTime(LocalDateTime.now())
                .subscription(subscription)
                .build();

        Coupon save = couponRepository.save(coupon);

        log.info("[useCoupon] OK!");
        return save;
    }

}
