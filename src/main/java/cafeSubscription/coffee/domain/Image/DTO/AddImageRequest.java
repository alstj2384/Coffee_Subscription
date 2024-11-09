package cafeSubscription.coffee.domain.image.DTO;

import cafeSubscription.coffee.domain.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddImageRequest {
    private String imagePath;

    public Image toEntity() {
        return Image.builder()
                .imagePath(imagePath)
                .iCreateAt(LocalDateTime.now())
                .build();

    }
}
