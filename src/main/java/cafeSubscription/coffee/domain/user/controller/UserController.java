package cafeSubscription.coffee.domain.user.controller;

import cafeSubscription.coffee.domain.user.dto.NickNameDTO;
import cafeSubscription.coffee.domain.user.entity.User;
import cafeSubscription.coffee.domain.user.repository.UserRepository;
import cafeSubscription.coffee.domain.user.service.UserService;
import cafeSubscription.coffee.global.config.CustomException;
import cafeSubscription.coffee.global.config.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "유저 닉네임 수정 API")
@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Operation(summary = "사용자 닉네임 변경 API")
    @PatchMapping("/nickname")
    @PreAuthorize("hasRole('customer')")
    public ResponseEntity<String> updateNickName(@RequestBody NickNameDTO nickNameDTO, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authenticatedUser)  {


        String user = authenticatedUser.getUsername();
        Optional<User> user3 = userRepository.findByUsername(user);

        User foundUser = user3.orElseThrow(()-> new CustomException(ErrorCode.NON_EXISTENT_USER));
        User updatedUser =userService.updateNickname(foundUser.getUserId(), nickNameDTO.getNickName());


        // 변경된 닉네임을 반환
        return ResponseEntity.ok(updatedUser.getNickName());
    }
}
