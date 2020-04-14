package com.itheima.web.filter;

import com.itheima.utils.RRHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class RRFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //将此次请求 request对象和response放到 thread中
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;

        //使用threadlocal
        //ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
        //ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();

        RRHolder.REQUEST_THREADLOCAL.set(request);
        RRHolder.RESPONSE_THREADLOCAL.set(response);



        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
