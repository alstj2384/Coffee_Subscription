package cafeSubscription.coffee.domain.cafe.mapper;

import cafeSubscription.coffee.domain.cafe.dto.ImageDTO;
import cafeSubscription.coffee.domain.image.entity.Image;

public class ImageMapper {
    public static ImageDTO toImageDto(Image image) {
        return ImageDTO.builder()
                .imageId(image.getImageId())
                .imagePath(image.getImagePath())
                .build();

    }
}
