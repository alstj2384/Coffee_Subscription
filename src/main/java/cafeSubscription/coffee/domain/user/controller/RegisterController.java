package cafeSubscription.coffee.domain.user.controller;

import cafeSubscription.coffee.domain.user.dto.OAuthRegisterDTO;
import cafeSubscription.coffee.domain.user.dto.RegisterDTO;
import cafeSubscription.coffee.domain.user.entity.Business;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.BusinessRepository;
import cafeSubscription.coffee.domain.user.service.OAuthRegisterService;
import cafeSubscription.coffee.domain.user.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;
    private final OAuthRegisterService oAuthRegisterService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO) {
        User user = registerService.register(registerDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/google/oauth")
    public ResponseEntity<User> registerOAuth(@RequestBody OAuthRegisterDTO oauthRegisterDTO ){
        User user = oAuthRegisterService.registerOAuthUser(oauthRegisterDTO);
        return ResponseEntity.ok(user);
    }
}
