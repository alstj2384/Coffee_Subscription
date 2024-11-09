package cafeSubscription.coffee.domain.user.mapper;

import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;

public class RegisterMapper {

    public static User toEntity(RegisterDTO registerDTO, String encodedPassword) {
        return User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(encodedPassword)
                .nickName(registerDTO.getNickName())
                .username(registerDTO.getUsername())
                .build();
    }
}
