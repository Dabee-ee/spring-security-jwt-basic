package dababy.springsecurityjwtbasic.config;

import dababy.springsecurityjwtbasic.filter.MyFilter3;
import dababy.springsecurityjwtbasic.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    // 이와 같이 사용하면 필터를 굳이 security filter 앞, 뒤에 넣어주지 않아도 됨.
    // 단, 우선 실행되는 필터는 security filter 이다.
    // 따라서 security filter 보다 먼저 동작하거나 바로 뒤에 동작해야 되는 필터는 security config 를 통해 설정해주자.

    @Bean
    public FilterRegistrationBean<MyFilter3> filter1() {
        FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); // 낮은 번호가 필터 중에서 가장 먼저 실행 됨.
        return bean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> filter2() {
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*");
        bean.setOrder(1); // 낮은 번호가 필터 중에서 가장 먼저 실행 됨.
        return bean;
    }
}
