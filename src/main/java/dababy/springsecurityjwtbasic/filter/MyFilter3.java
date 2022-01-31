package dababy.springsecurityjwtbasic.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Servlet Request & Response down casting
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");

//        if(req.getMethod().equals("POST")) {
//            System.out.println("POST 요청됨.");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);

//            if (headerAuth.equals("CORS")) {
                chain.doFilter(req, res); // 해당 필터로 끝나지 않게 하려면 chain 에다가 넣어주어야 함.
//            } else {
//                PrintWriter out = res.getWriter();
//                out.println("인증되지 않았음.");
//            }
        }

//        System.out.println("필터 테스트 - 3");
//    }

}
