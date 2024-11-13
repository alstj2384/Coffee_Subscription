package cafeSubscription.coffee.domain.user.service.JWT;

import cafeSubscription.coffee.domain.user.dto.LoginDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final RegisterRepository registerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh_token.expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Map<String, String> login(LoginDTO loginDTO) {
        User user = registerRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_MISMATCH));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        // 액세스 및 리프레시 토큰 생성
        String accessToken = generateAccessToken(user.getUsername());
        String refreshToken = generateRefreshToken(user.getUsername());

        // 토큰 반환
        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    public String generateAccessToken(String username) {
        return generateToken(username, accessTokenExpiration);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, refreshTokenExpiration);
    }

    private String generateToken(String subject, long duration) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}

