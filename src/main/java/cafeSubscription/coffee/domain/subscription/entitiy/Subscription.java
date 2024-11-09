package cafeSubscription.coffee.domain.subscription.entitiy;

import cafeSubscription.coffee.domain.cafe.entity.Coupon;
import cafeSubscription.coffee.domain.subscription.SubscriptionType;
import cafeSubscription.coffee.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @Column(nullable = false)
    private SubscriptionType subscriptionType;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private Integer usedCount;

    @Column(nullable = true)
    private LocalDateTime lastUsedDate;

    @OneToOne(mappedBy = "subscription", fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coupon> coupons;
}
