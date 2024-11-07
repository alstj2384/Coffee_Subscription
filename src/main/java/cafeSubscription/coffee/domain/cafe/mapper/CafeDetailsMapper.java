package cafeSubscription.coffee.domain.cafe.mapper;

import cafeSubscription.coffee.domain.cafe.dto.CafeDetailsDTO;
import cafeSubscription.coffee.domain.cafe.dto.ImageDTO;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.domain.image.entity.ImageAll;
import cafeSubscription.coffee.global.exception.CustomException;
import cafeSubscription.coffee.global.exception.ErrorCode;

import java.awt.*;

public class CafeDetailsMapper {
    public static CafeDetailsDTO toCafeDetailDto(Cafe cafe) {
        if(cafe == null){
            throw new CustomException(ErrorCode.CAFE_NOT_FOUND);
        }
        return CafeDetailsDTO.builder()
                .cafeId(cafe.getCafeId())
                .cafeName(cafe.getCafeName())
                .pin(cafe.getPin())
                .role(cafe.getBusiness().getUser().getRole().name())
                .image(ImageMapper.toImageDto(cafe.getImageAll()))
                .build();
    }
}


