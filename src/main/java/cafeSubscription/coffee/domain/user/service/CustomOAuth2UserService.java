package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.service.JWT.JwtTokenUtil;
import cafeSubscription.coffee.domain.user.service.OAuthRegisterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuthRegisterService oAuthRegisterService;
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser 메소드 호출됨: {}", userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth Provider에서 사용자 고유 ID 추출
        String oauthProviderId = (String) oAuth2User.getAttributes().get("sub");

        // DB에서 사용자가 존재하는지 확인
        Optional<User> existingUser = oAuthRegisterService.findUserByOauthProviderId(oauthProviderId);

        User user;
        if (existingUser.isEmpty()) {
            OAuthRegisterDTO oauthRegisterDTO = new OAuthRegisterDTO();
            oauthRegisterDTO.setEmail((String) oAuth2User.getAttributes().get("email"));
            oauthRegisterDTO.setName((String) oAuth2User.getAttributes().get("name"));
            oauthRegisterDTO.setOauthProviderId(oauthProviderId);
            user = oAuthRegisterService.registerOAuthUser(oauthRegisterDTO);
        } else {
            // 기존 사용자가 있을 경우
            user = existingUser.get();
        }

        String accessToken = jwtTokenUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getUsername());

        // OAuth2User에 토큰 포함
        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.put("accessToken", accessToken);
        attributes.put("refreshToken", refreshToken);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                attributes,
                "oauthProviderId"

        );
    }
}

