package cafeSubscription.coffee.domain.user.controller;

import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginTestController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User != null) {
            // 로그인된 사용자 이름을 가져와서 model에 추가
            String userName = (String) oAuth2User.getAttributes().get("name");
            model.addAttribute("userName", userName);
        }
        return "list"; // index.html 파일을 렌더링합니다.
    }
}
