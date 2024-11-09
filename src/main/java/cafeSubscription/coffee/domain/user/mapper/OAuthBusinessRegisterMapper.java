package cafeSubscription.coffee.domain.user.mapper;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.entity.User;

public class OAuthBusinessRegisterMapper {
    public static Business toOAuthBusinessEntity(OAuthRegisterDTO oauthRegisterDTO, User user){
        return Business.builder()
                .businessNumber(oauthRegisterDTO.getBusinessNumber())
                .bName(oauthRegisterDTO.getBName())
                .bankAccount(oauthRegisterDTO.getBankAccount())
                .openingDate(oauthRegisterDTO.getOpeningDate())
                .user(user)
                .build();
    }
}
