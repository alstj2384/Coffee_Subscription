package cafeSubscription.coffee.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OAuthRegisterDTO {
    private String email;
    private String name;
    private String nickName;
    private String oauthProviderId;

    //비지니스 사용자 필요정보
    private String businessNumber;

    @JsonProperty("bName")
    private String bName;
    private String bankAccount;
    private LocalDate openingDate;
}
