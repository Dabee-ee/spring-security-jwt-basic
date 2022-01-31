package dababy.springsecurityjwtbasic.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import dababy.springsecurityjwtbasic.config.auth.PrincipalDetails;
import dababy.springsecurityjwtbasic.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// "/login"이라는 url로 요청이 들어올 때 동작하는 필터인 UsernamePasswordAuthenticationFilter
// 하지만, 토큰을 사용하기 위해 formlogin을 disabled 했기 때문에,
// 스프링 시큐리티 필터에 직접 해당 필터를 등록해주어야 한다.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {}


    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도 중");

        // 1. get username , password
        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println(input);

            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);
            System.out.println(member);

            // make token
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken); // PrincipalDetailsService의 loadUserByUsername()이 실행됨. -> 그럼 authentication 에 인증 정보가 담긴다.

            // authentication 객체가 session 영역에 저장됨. => 로그인이 되었다는 것.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUsername());

            return authentication;


        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. login (authenticationManager로 로그인 시도를 하면 principal details 호출 -> load by username 실행 )


        // 3. return된 principal details를 세션에 담고

        // 4. jwt 토큰 발급 및 응답 ( 권한 관리를 위해 )
        return null;
    }

    // attemptAuthentication 실행 후, 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행 됨.
    // 위에서 하지 않고, 여기서 JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 응답해주는 것도 가능.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // Hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                .withClaim("id", principalDetails.getMember().getId())
                .withClaim("username", principalDetails.getMember().getUsername())
                .sign(Algorithm.HMAC512("cos"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
