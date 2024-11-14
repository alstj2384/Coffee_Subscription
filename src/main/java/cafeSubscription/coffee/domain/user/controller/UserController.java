package cafeSubscription.coffee.domain.user.controller;

import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.service.UserService;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 닉네임 수정 API")
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(summary = "사용자 닉네임 변경 API")
    @PatchMapping("/{userId}/nickname")
    public ResponseEntity<String> updateNickName(@PathVariable long userId, @RequestBody String nickname) {



        log.info(String.valueOf(userId));
        // UserService를 통해 userId에 해당하는 사용자 가져오기
        User user = userService.findById(userId);
        log.info(String.valueOf(user.getUserId()));
        if (user == null) {
            log.info(String.valueOf(user.getUserId()));
            throw new CustomException(ErrorCode.NON_EXISTENT_USER);
        }


        User updatedUser = userService.updateNickname(userId, nickname);

        // 변경된 닉네임을 반환
        return ResponseEntity.ok(updatedUser.getNickName());
    }
}
