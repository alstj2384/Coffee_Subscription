package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GoogleOAuthService {

    public Map<String, Object> getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v3/userinfo"; //구글사용자 정보 API

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Bearer토큰인증 헤더 추가(API 접근 위함)

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        return response.getBody();
    }

    //사용자 정보 매핑
    public OAuthRegisterDTO createOAuthRegisterDTO(String accessToken) {

        Map<String, Object> userInfo = getUserInfo(accessToken);


        OAuthRegisterDTO oauthRegisterDTO = new OAuthRegisterDTO();
        oauthRegisterDTO.setEmail(userInfo.get("email").toString());
        oauthRegisterDTO.setName(userInfo.get("name").toString());
        oauthRegisterDTO.setOauthProviderId(userInfo.get("sub").toString());

        return oauthRegisterDTO;
    }
}
