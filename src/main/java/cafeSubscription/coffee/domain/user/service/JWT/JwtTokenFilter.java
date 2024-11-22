package cafeSubscription.coffee.domain.user.service.JWT;

import cafeSubscription.coffee.domain.user.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.substring(7);
            log.info("JWT Token: {}", token);
            if (jwtTokenUtil.validateToken(token)) {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                log.info("username : {}", username);
                if (username != null) {
                    log.info("username1 : {}", username);
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    log.info("username2 : {}", userDetails.getUsername());
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    log.info(userDetails.getUsername());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info(authentication.getDetails().toString());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    Authentication authentications = SecurityContextHolder.getContext().getAuthentication();
                    log.info(authentications.toString());
                    String authenticatedEmail = authentication.getName();
                    log.info(authenticatedEmail);
                }
            }
        } catch (Exception ex) {
            logger.error("JWT 인증 필터에서 오류 발생: {}", ex);
        }
        filterChain.doFilter(request, response);
    }
}