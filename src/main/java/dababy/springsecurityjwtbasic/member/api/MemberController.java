package dababy.springsecurityjwtbasic.member.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("home")
    public String home() {
        return " <h1>home</h1>";
    }

//    @GetMapping("user")
//    public String user(Authentication authentication) {
//
//        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//
//    }


}
