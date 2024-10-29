package cafeSubscription.coffee.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String nickName;
    private String username; //사용자 아이디
}
