package cafeSubscription.coffee.domain.user.controller;

import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.service.UserService;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //일반 사용자 닉네임변경
    @PatchMapping("/{userId}/nickname")
    public ResponseEntity<String> updateNickName(@PathVariable long userId, @RequestBody String nickname) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = authentication.getName();


        // UserService를 통해 userId에 해당하는 사용자 가져오기
        User user = userService.findById(userId);
        if (user == null) {
            throw new CustomException(ErrorCode.NON_EXISTENT_USER);
        }

        // 현재 인증된 사용자가 요청한 userId와 동일한지 검증
        if (!user.getEmail().equals(authenticatedEmail)) {
            throw new CustomException(ErrorCode.USER_MISMATCH);
        }

        User updatedUser = userService.updateNickname(userId, nickname);

        // 변경된 닉네임을 반환
        return ResponseEntity.ok(updatedUser.getNickName());
    }
}
