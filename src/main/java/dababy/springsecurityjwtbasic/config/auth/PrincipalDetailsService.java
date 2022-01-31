package dababy.springsecurityjwtbasic.config.auth;

import dababy.springsecurityjwtbasic.member.entity.Member;
import dababy.springsecurityjwtbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(" loadUserByUsername of Principal details service");
        Member member = memberRepository.findByUsername(username);

        if ( member == null ) {
            throw new UsernameNotFoundException(username);
        }

        return new PrincipalDetails(member);
    }

}
