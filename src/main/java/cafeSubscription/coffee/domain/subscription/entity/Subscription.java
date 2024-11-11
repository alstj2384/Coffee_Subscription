package cafeSubscription.coffee.domain.subscription.entity;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.subscription.dto.SubscriptionType;
import cafeSubscription.coffee.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    @Column(nullable = false)
    private LocalDateTime startDate;

    // 이부분 추가됨
    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer usedCount;

    @Column(nullable = true)
    private LocalDateTime lastUsedDate;

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return startDate.isBefore(now) && endDate.isAfter(now);
    }

    public boolean isUsedToday(){
        LocalDateTime now = LocalDateTime.now();
        if(lastUsedDate == null){
            return false;
        }
        if(now.getDayOfMonth() == lastUsedDate.getDayOfMonth()){
            return true;
        }
        return false;
    }

    public void useCoupon(){
        usedCount += 1;
        lastUsedDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
