package cafeSubscription.coffee.domain.user.controller;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.service.JWT.JwtService;
import cafeSubscription.coffee.domain.user.service.OAuthRegisterService;
import cafeSubscription.coffee.domain.user.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "회원가입 API", description = "JWT 자체 회원가입 & google OAuth login API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final OAuthRegisterService oAuthRegisterService;
    private final JwtService jwtService;

    @Operation(summary = "JWT 자체 회원가입 API", description = "일반 사용자(이름, 이메일, 비밀번호, 닉네임, 아이디), 비지니스(카페사장)는 추가로 사업자번호, 사업자이름, 계좌비밀번호, 사업자개업날짜 추가로 작성필요 ")

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDTO registerDTO) {
        User user = registerService.register(registerDTO);

        //회원가입 후 액세스 토큰 발급
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        // 토큰을 헤더에 포함
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Authorization-refresh", refreshToken);

        // 토큰을 응답 본문에도 포함
        Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);


        // 토큰 반환
        return new ResponseEntity<>(tokens, headers, HttpStatus.OK);
    }

    @Operation(summary = "OAuth로 가입후 닉네임 값 받는 API", description = "일반 사용자 : OAuth 경우 먼제 사용자가 로그인 후 자동으로 DB저장 후 닉네임 값 받음, 비지니스 사용자 : 추가로 사업자번호, 사업자이름, 계좌비밀번호, 사업자개업날짜 필요")
    @PostMapping("/register/oauth/update")
    public ResponseEntity<User> updateOAuthUser(@AuthenticationPrincipal OAuth2User principal, @RequestBody OAuthRegisterDTO requestDto) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String oauthProviderId = principal.getAttribute("sub");
        User updatedUser = oAuthRegisterService.updateOAuthUserByProviderId(oauthProviderId, requestDto.getNickName(), requestDto.getBusinessNumber() != null, requestDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
