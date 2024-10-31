package cafeSubscription.coffee.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String nickName;
    private String username; //사용자 아이디

    //비지니스 사용자 필요정보
    private String businessNumber;
    private String bName;
    private String bankAccount;
    private LocalDate openingDate;
}
