package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.dto.LoginDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RegisterRepository registerRepository;
    private final PasswordEncoder passwordEncoder;
    private final String secretkey = "arabica"; //보증서

    public Map<String, String> login(LoginDTO loginDTO) {
        User user = registerRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        //액세스 및 리프레시 토큰 생성 메서드
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        //토큰반환
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }
    // 액세스 토큰 생성
    private String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30분 유효
                .signWith(SignatureAlgorithm.HS256, secretkey) // 서명
                .compact(); //문자열로 토큰 반환
    }

    // 리프레시 토큰 생성
    private String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일 유효
                .signWith(SignatureAlgorithm.HS256, secretkey)
                .compact();
    }
}
