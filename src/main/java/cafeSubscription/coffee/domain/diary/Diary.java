package cafeSubscription.coffee.domain.diary;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @ManyToOne
    private Cafe cafe;

    // TODO BusinessId, UserId 모두 연관?

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String diaryContent;

    @Column(nullable = false)
    private Integer view;

    @CreationTimestamp
    private LocalDateTime entryDate;
}
