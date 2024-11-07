package cafeSubscription.coffee.domain.user.service;


import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.global.exception.CustomException;
import cafeSubscription.coffee.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 조회
    public User findById(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NON_EXISTENT_USER);
        }
        return userOptional.get();
    }

    public User updateNickname(Integer userId, String newNickname) {
        User user = findById(userId);

        user.setNickName(newNickname);

        return userRepository.save(user);
    }
}
