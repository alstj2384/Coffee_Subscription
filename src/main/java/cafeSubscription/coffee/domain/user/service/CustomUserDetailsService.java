package cafeSubscription.coffee.domain.user.service;

import cafeSubscription.coffee.domain.user.repository.RegisterRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final RegisterRepository registerRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return registerRepository.findByUsername(username)
                    .map(user -> org.springframework.security.core.userdetails.User.builder()
                            //todo  org.springframework.security.core.userdetails.User.builder() 메서드에서 제공하는 메서드는 .username(), .password(), .roles()

                            //시큐리티에서 제공해주는 메서드가 한정적이라 위와 같이 username이지만 사용자 Id 불러옴
                            .username(String.valueOf(user.getUserId()))
                            .password(user.getPassword())
                            .roles(user.getRole().name())
                            .build())
                    .orElseThrow(() -> new CustomException(ErrorCode.DUPLICATE_USER_USERNAME));
        }
}
