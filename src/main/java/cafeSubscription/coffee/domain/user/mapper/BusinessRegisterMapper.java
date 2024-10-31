package cafeSubscription.coffee.domain.user.mapper;

import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.entity.User;


public class BusinessRegisterMapper {
    

    public static Business toBusinessEntity(RegisterDTO registerDTO, User user) {
        return Business.builder()
                .businessNumber(registerDTO.getBusinessNumber())
                .bName(registerDTO.getBName())
                .bankAccount(registerDTO.getBankAccount())
                .openingDate(registerDTO.getOpeningDate())
                .user(user) // 1대1 관계 설정
                .build();
    }
}
