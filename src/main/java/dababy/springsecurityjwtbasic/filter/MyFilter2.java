package dababy.springsecurityjwtbasic.filter;

import javax.servlet.*;
import java.io.IOException;


public class MyFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터 테스트 - 2");
        chain.doFilter(request, response); // 해당 필터로 끝나지 않게 하려면 chain 에다가 넣어주어야 함.
    }

}
