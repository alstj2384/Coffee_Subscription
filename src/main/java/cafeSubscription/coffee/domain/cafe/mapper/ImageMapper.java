package cafeSubscription.coffee.domain.cafe.mapper;

import cafeSubscription.coffee.domain.cafe.dto.ImageDTO;
import cafeSubscription.coffee.domain.image.entity.ImageAll;

import java.awt.*;

public class ImageMapper {
    public static ImageDTO toImageDto(ImageAll imageAll) {
        return ImageDTO.builder()
                .imageId(imageAll.getImageId())
                .imageUrl(imageAll.getImageUrl())
                .build();

    }
}
