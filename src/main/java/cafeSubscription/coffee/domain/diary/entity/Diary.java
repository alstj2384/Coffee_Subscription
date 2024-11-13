package cafeSubscription.coffee.domain.diary.entity;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.diary.DTO.UpdateDiaryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column
    private Integer view; // TODO - 조회수 기능

    @CreationTimestamp
    private LocalDateTime entryDate; // 수정날짜 없앰

    @ElementCollection
    private List<String> thumbnail; // 임시 이미지 정보 TODO - 사진 추가

    public void update(String title, String diaryContent) {
        this.title = title;
        this.diaryContent = diaryContent;
    }

    public void update2(UpdateDiaryRequest request){
        if(request.getTitle() != null) this.title = request.getTitle();
        if(request.getDiaryContent() != null) this.diaryContent = request.getDiaryContent();
    }
}
