package cafeSubscription.coffee.domain.subscription.service;

import cafeSubscription.coffee.domain.subscription.dto.SubscriptionType;
import cafeSubscription.coffee.domain.subscription.entity.Subscription;
import cafeSubscription.coffee.domain.subscription.repository.SubscriptionRepository;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Subscription subscription(Long userId, SubscriptionType type) {

        // 유저 정보 검증
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException(ErrorCode.NON_EXISTENT_USER.getMsg()));

        // 중복 구독 검증
        Optional<List<Subscription>> subscriptions = subscriptionRepository.findAllByUserUserIdOrderByStartDateDesc(user.getUserId());

        subscriptions.ifPresent(e -> {
            if(!e.isEmpty() && e.get(0).isValid())
                throw new IllegalArgumentException(ErrorCode.SUBSCRIPTION_ALREADY_EXISTS.getMsg());
        });


        Subscription subscription = Subscription.builder()
                .subscriptionType(type)
                .usedCount(0)
                .user(user)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(type.getDuration()))
                .build();

        return subscriptionRepository.save(subscription);

    }
}
