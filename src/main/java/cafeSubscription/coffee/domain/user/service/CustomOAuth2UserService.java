package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;
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
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {


        private final OAuthRegisterService oAuthRegisterService;

        @Autowired
        public CustomOAuth2UserService(OAuthRegisterService oAuthRegisterService) {
            this.oAuthRegisterService = oAuthRegisterService;
        }

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            log.info("loadUser 메소드 호출됨: {}", userRequest);
            OAuth2User oAuth2User = super.loadUser(userRequest);

            // OAuth2 제공자 확인
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            Map<String, Object> attributes = oAuth2User.getAttributes();

            // 사용자 정보 가져오기
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            String oauthProviderId = (String) attributes.get("sub"); // Google에서 제공하는 고유 ID

            log.info("OAuth User Info - email: {}, name: {}, oauthProviderId: {}", email, name, oauthProviderId);

            // DTO 생성 및 사용자 정보 데이터베이스에 저장 (세션 사용 대신)
            OAuthRegisterDTO oauthRegisterDTO = new OAuthRegisterDTO();
            oauthRegisterDTO.setEmail(email);
            oauthRegisterDTO.setName(name);
            oauthRegisterDTO.setOauthProviderId(oauthProviderId);

            // OAuth 사용자 정보 데이터베이스에 저장
            User user = oAuthRegisterService.registerOAuthUser(oauthRegisterDTO);

            // 사용자 정보 매핑
            attributes.put("email", email);
            attributes.put("name", name);
            attributes.put("oauthProviderId", oauthProviderId);

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

            return new DefaultOAuth2User(Collections.singleton(authority), attributes, "email");
        }
    }

