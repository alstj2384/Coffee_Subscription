package cafeSubscription.coffee.domain.user.entity;


import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.subscription.entity.Subscription;
import cafeSubscription.coffee.domain.user.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // H2 DB에서 user가 예약어인 관계로 users로 테이블명 임시 변경
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickName;
    @Column(nullable = true)
    private String username;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = true, unique = true)
    private String oauthProviderId; //oauth 사용자 고유 ID값


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

//    @Column(nullable = true)
//    private String oauthProvider; //ouath가 구글, 카카오인지 구별 (추후 추가시 필요로 보류)


}
