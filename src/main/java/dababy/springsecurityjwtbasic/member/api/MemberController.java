package dababy.springsecurityjwtbasic.member.api;

import dababy.springsecurityjwtbasic.config.auth.PrincipalDetails;
import dababy.springsecurityjwtbasic.member.entity.Member;
import dababy.springsecurityjwtbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("home")
    public String home() {
        return " <h1>home</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody Member member) {
        member.setUsername(member.getUsername());
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        memberRepository.save(member);

        return "회원 가입 완료";
    }

    @GetMapping("user")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principal.getUsername());
        return "user";
    }





}
