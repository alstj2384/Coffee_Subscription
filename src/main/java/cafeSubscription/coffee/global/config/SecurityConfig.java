package cafeSubscription.coffee.global.config;

import cafeSubscription.coffee.domain.user.service.CustomOAuth2UserService;
import cafeSubscription.coffee.domain.user.service.CustomUserDetailsService;
import cafeSubscription.coffee.domain.user.service.JWT.JwtTokenFilter;
import cafeSubscription.coffee.domain.user.service.JWT.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 사용 허가해줌
public class SecurityConfig {


    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("admin > owner \n owner > customer");
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfig - CustomOAuth2UserService 빈 주입 완료: {}", customOAuth2UserService != null);
        http
                .csrf(csrf -> csrf.disable())            // CSRF 비활성화
                .formLogin(formLogin -> formLogin.disable()) // 폼 로그인 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())) // X-Frame-Options 비활성화
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/user/**", "/h2-console/**", "/login", "/oauth2/**","/**","/login/oauth2/code/*").permitAll() // 공개 URL
                        .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한이 필요한 URL
                        .anyRequest().authenticated()) // 나머지 URL은 인증 필요
                .oauth2Login(oauth2 -> oauth2
                       // .loginProcessingUrl("http://localhost:8080/login/oauth2/code/google")
                        .userInfoEndpoint(userInfo -> {
                            log.info("SecurityConfig: OAuth2 로그인 과정 - 사용자 정보 엔드포인트 요청됨");
                            userInfo.userService(customOAuth2UserService);
                        }))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 세션 정책 설정
                .logout(logout -> logout
                                .logoutUrl("/logout") // 로그아웃 요청 경로 설정
                                .logoutSuccessUrl("/login") // 로그아웃 후 리다이렉트 경로
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")); // 쿠키 삭제

        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenUtil, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
