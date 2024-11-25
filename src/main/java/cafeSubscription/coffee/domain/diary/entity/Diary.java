package cafeSubscription.coffee.domain.diary.entity;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String diaryContent;

    @Column
    private Integer view; //아직 구현 안함

    @CreationTimestamp
    private LocalDateTime entryDate;

    public void update(String title, String diaryContent) {
        this.title = title;
        this.diaryContent = diaryContent;
    }
}
