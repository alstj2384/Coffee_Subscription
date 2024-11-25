package cafeSubscription.coffee.domain.image.entity;

import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.custom.ImageType;
import cafeSubscription.coffee.domain.custom.ImageTypeConverter;
import cafeSubscription.coffee.domain.diary.entity.Diary;
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
@Table(name = "imageAll")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String imagePath;

    @Convert(converter = ImageTypeConverter.class)
    private ImageType imageType;

    @CreationTimestamp
    private LocalDateTime iCreateAt;

}
