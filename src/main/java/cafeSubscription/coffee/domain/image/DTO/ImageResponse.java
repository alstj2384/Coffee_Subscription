package cafeSubscription.coffee.domain.image.DTO;

import cafeSubscription.coffee.domain.image.entity.Image;
import lombok.Getter;

@Getter
public class ImageResponse {
    private final Long imageId;
    private final String imagePath;

    public ImageResponse(Image image) {
        this.imageId = image.getImageId();
        this.imagePath = image.getImagePath();
    }
}
