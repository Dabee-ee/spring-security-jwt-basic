package dababy.springsecurityjwtbasic.config;

import dababy.springsecurityjwtbasic.filter.MyFilter1;
import dababy.springsecurityjwtbasic.filter.MyFilter3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됩니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);

        http.addFilter(corsConfig.corsFilter()); // @CrossOrigin -> 인증 x , 인증이 있는 경우는 시큐리티 필터에 등록을 해줘야 함.

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();


        http.authorizeRequests()
                .mvcMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .mvcMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .mvcMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }


}
