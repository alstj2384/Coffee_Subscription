package cafeSubscription.coffee.domain.cafe.service;

import cafeSubscription.coffee.domain.cafe.dto.StatsDTO;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.cafe.mapper.StatsMapper;
import cafeSubscription.coffee.domain.cafe.repository.CafeRepository;
import cafeSubscription.coffee.domain.cafe.repository.CouponRepository;
import cafeSubscription.coffee.domain.diary.repositoty.DiaryRepository;
import cafeSubscription.coffee.domain.review.repository.ReviewRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final CafeRepository cafeRepository;
    private final CouponRepository couponRepository;
    private final ReviewRepository reviewRepository;
    private final DiaryRepository diaryRepository;

    public StatsDTO getSummary(Long cafeId, String ownerEmail) {
        Cafe cafe = validateCafeOwnership(cafeId, ownerEmail);
        int dailyUsage = couponRepository.countDailyUsage(cafe.getCafeId(), LocalDate.now());
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(today.getDayOfWeek().getValue() - 1);
        int weeklyUsage = couponRepository.countUsageBetweenDates(cafe.getCafeId(), weekStart, today);
        int totalReviewCount = reviewRepository.countByCafe_CafeId(cafe.getCafeId());
        int totalDiaryCount = diaryRepository.countByCafe_CafeId(cafe.getCafeId());

        return StatsMapper.toStatsDTO(dailyUsage, weeklyUsage, totalReviewCount, totalDiaryCount);
    }

    public int getPeriodCouponUsage(Long cafeId, String ownerEmail, LocalDate startDate, LocalDate endDate) {
        Cafe cafe = validateCafeOwnership(cafeId, ownerEmail);
        return couponRepository.countUsageBetweenDates(cafeId, startDate, endDate);
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
