package cafeSubscription.coffee.domain.cafe.mapper;

import cafeSubscription.coffee.domain.cafe.DTO.CafeDetailsDTO;
import cafeSubscription.coffee.domain.cafe.entity.Cafe;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;

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
                .image(ImageMapper.toImageDto(cafe.getImage()))
                .build();
    }
}


