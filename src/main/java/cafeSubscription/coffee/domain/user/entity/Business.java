package cafeSubscription.coffee.domain.user.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    @Column(nullable = true)
    private String businessNumber;

    @Column(nullable = true)
    private String bName;

    @Column(nullable = true)
    private String bankAccount;

    @Column(nullable = true)
    private LocalDate openingDate; // 개업일자, 일반 사용자는 null 가능

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 1대1
}