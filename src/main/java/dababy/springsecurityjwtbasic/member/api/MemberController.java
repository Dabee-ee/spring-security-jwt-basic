package dababy.springsecurityjwtbasic.member.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("home")
    public String home() {
        return " <h1>home</h1>";
    }
}
