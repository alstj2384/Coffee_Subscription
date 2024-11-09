package cafeSubscription.coffee.domain.Image.DTO;

import cafeSubscription.coffee.domain.Image.Image;
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
