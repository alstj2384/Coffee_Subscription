package cafeSubscription.coffee.domain.user.service;


import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 조회
    @PreAuthorize("hasRole('customer')")
    public User findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NON_EXISTENT_USER);
        }
        return userOptional.get();
    }

    @PreAuthorize("hasRole('customer')")
    public User updateNickname(long userId, String newNickname) {
        User user = findById(userId);

        user.setNickName(newNickname);

        return userRepository.save(user);
    }
}
