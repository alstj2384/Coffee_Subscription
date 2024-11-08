package cafeSubscription.coffee.domain.Image;

import cafeSubscription.coffee.domain.custom.ImageType;
import cafeSubscription.coffee.domain.custom.ImageTypeConverter;
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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;

    @Column(nullable = false)
    private String imagePath;

    @Convert(converter = ImageTypeConverter.class)
    private ImageType imageType;

    @CreationTimestamp
    private LocalDateTime iCreateAt;
}
