package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.service.OAuthRegisterService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuthRegisterService oAuthRegisterService;

    public CustomOAuth2UserService(OAuthRegisterService oAuthRegisterService) {
        this.oAuthRegisterService = oAuthRegisterService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 정보 가져오기
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String oauthProviderId = oAuth2User.getAttribute("sub");

        // DTO 생성 및 정보 저장
        OAuthRegisterDTO oauthRegisterDTO = new OAuthRegisterDTO();
        oauthRegisterDTO.setEmail(email);
        oauthRegisterDTO.setName(name);
        oauthRegisterDTO.setOauthProviderId(oauthProviderId);


        User user = oAuthRegisterService.registerOAuthUser(oauthRegisterDTO);

        // 사용자의 권한 설정
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // 사용자 정보 매핑
        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.put("userId", user.getUserId());
        attributes.put("email", user.getEmail());
        attributes.put("name", user.getName());
        attributes.put("role", user.getRole().name());

        // OAuth2User 반환
        return new DefaultOAuth2User(Collections.singleton(authority), attributes, "email");
    }
}
