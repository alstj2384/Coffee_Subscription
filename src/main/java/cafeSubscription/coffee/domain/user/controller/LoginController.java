package cafeSubscription.coffee.domain.user.controller;

import cafeSubscription.coffee.domain.user.dto.LoginDTO;
import cafeSubscription.coffee.domain.user.service.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;


import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class LoginController {

    private final JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Map<String, String> tokens = jwtService.login(loginDTO);

        // 토큰을 헤더에 포함
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokens.get("accessToken"));
        headers.set("Authorization-refresh", tokens.get("refreshToken"));

        return new ResponseEntity<>(tokens, headers, HttpStatus.OK);
    }


}
