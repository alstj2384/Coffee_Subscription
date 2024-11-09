package cafeSubscription.coffee.domain.user.controller;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.service.OAuthRegisterService;
import cafeSubscription.coffee.domain.user.service.RegisterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final OAuthRegisterService oAuthRegisterService;

    @Autowired
    private HttpSession httpSession;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO) {
        User user = registerService.register(registerDTO);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/register/google/oauth")
    public ResponseEntity<User> registerOAuth(@RequestBody OAuthRegisterDTO requestDto, HttpSession session ){
        // 세션에서 OAuth 사용자 정보 가져오기
        OAuthRegisterDTO oauthRegisterDTO = (OAuthRegisterDTO) session.getAttribute("oauthUser");

        if (oauthRegisterDTO == null) {
            throw new IllegalStateException("OAuth 정보가 세션에 존재하지 않습니다. 로그인을 다시 시도하세요.");
        }

        // 세션에서 가져온 값과 바디에서 받은 값을 병합
        oauthRegisterDTO.setNickName(requestDto.getNickName()); // 닉네임 설정
        oauthRegisterDTO.setBusinessNumber(requestDto.getBusinessNumber()); // 비즈니스 정보 설정
        oauthRegisterDTO.setBName(requestDto.getBName());
        oauthRegisterDTO.setBankAccount(requestDto.getBankAccount());
        oauthRegisterDTO.setOpeningDate(requestDto.getOpeningDate());

        // 병합된 DTO를 이용해 데이터베이스에 사용자 저장
        User savedUser = oAuthRegisterService.registerOAuthUser(oauthRegisterDTO);

        // ResponseEntity로 사용자 정보와 상태 코드 반환
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


}
