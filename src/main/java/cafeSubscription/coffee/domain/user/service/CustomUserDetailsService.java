package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_USER));

        UserBuilder builder = org.springframework.security.core.userdetails.User.builder();
        return builder
                .username(user.getUsername())  // 로그인 ID
                .password(user.getPassword())
                .roles(user.getRole().name()) // roles 메서드는 접두사 "ROLE_"  권한 앞에 붙여줌
                .build();
    }
}
