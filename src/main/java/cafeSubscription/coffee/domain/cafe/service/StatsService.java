package cafeSubscription.coffee.domain.cafe.service;

import cafeSubscription.coffee.domain.cafe.DTO.StatsDTO;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.mapper.StatsMapper;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.coupon.repository.CouponRepository;
import cafeSubscription.coffee.domain.diary.repositoty.DiaryRepository;
import cafeSubscription.coffee.domain.review.repository.ReviewRepository;
import cafeSubscription.coffee.domain.subscription.repository.SubscriptionRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final CafeRepository cafeRepository;
    private final CouponRepository couponRepository;
    private final ReviewRepository reviewRepository;
    private final DiaryRepository diaryRepository;
    private final SubscriptionRepository subscriptionRepository;

    @PreAuthorize("hasRole('owner')")
    public StatsDTO getSummary(Long cafeId, String ownerEmail) {
        Cafe cafe = validateCafeOwnership(cafeId, ownerEmail);

        int totalReviewCount = reviewRepository.countByCafe_CafeId(cafe.getCafeId());
        int totalDiaryCount = diaryRepository.countByCafe_CafeId(cafe.getCafeId());

        return StatsMapper.toStatsDTO(totalReviewCount, totalDiaryCount);
    }

    @PreAuthorize("hasRole('owner')")
    public int getPeriodCouponUsage(Long cafeId, String ownerEmail, LocalDateTime startDate, LocalDateTime endDate) {
        // 카페 소유권 검증
        Cafe cafe = validateCafeOwnership(cafeId, ownerEmail);

        // 해당 기간의 쿠폰 사용량 집계
        return subscriptionRepository.countUsageBetweenDates(cafeId, startDate, endDate);
    }

    private Cafe validateCafeOwnership(Long cafeId, String ownerEmail) {
        Optional<Cafe> cafeOptional = cafeRepository.findById(cafeId);
        if (cafeOptional.isEmpty()) {
            throw new CustomException(ErrorCode.CAFE_NOT_FOUND);
        }
        Cafe cafe = cafeOptional.get();
        if (!cafe.getBusiness().getUser().getEmail().equals(ownerEmail)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        return cafe;
    }


}
