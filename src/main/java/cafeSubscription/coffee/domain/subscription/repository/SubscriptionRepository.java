package cafeSubscription.coffee.domain.subscription.repository;

import cafeSubscription.coffee.domain.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // 특정 사용자의 구독기록을 최신순으로 반환
    Optional<List<Subscription>> findAllByUserUserIdOrderByStartDateDesc(Long userId);
}
