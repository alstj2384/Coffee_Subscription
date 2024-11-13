package cafeSubscription.coffee.domain.user.mapper;

import cafeSubscription.coffee.domain.user.UserRole;
import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;

public class OAuthRegisterMapper {
    public static User toOAuthEntity(OAuthRegisterDTO oauthRegisterDTO){
        return User.builder()
                .email(oauthRegisterDTO.getEmail())
                .name(oauthRegisterDTO.getName())
                .oauthProviderId(oauthRegisterDTO.getOauthProviderId())
                .build();

    }
}
