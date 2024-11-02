package cafeSubscription.coffee.domain.diary;

import cafeSubscription.coffee.domain.cafe.Cafe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diaryId;

    @ManyToOne
    private Cafe cafe;

    // TODO BusinessId, UserId 모두 연관?

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String diaryContent;

    @Column
    private Integer view; //아직 구현 안함

    @CreationTimestamp
    private LocalDateTime entryDate;

    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정날짜(추가해도 되는지 모르겠슴다)

    @ElementCollection
    private List<String> thumbnail; // 임시 이미지 정보

    @Builder
    public Diary(String title, String diaryContent, List<String> thumbnail) {
        this.title = title;
        this.diaryContent = diaryContent;
        this.thumbnail = thumbnail != null ? new ArrayList<>(thumbnail) : new ArrayList<>();
    }

    public void update(String title, String diaryContent) {
        this.title = title;
        this.diaryContent = diaryContent;
        this.updatedAt = LocalDateTime.now();
    }
}
