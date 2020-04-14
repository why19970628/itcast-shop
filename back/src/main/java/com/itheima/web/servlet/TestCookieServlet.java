package com.itheima.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testcookie")
public class TestCookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //测试cookie的问题
        Cookie test = new Cookie("test", "123");
        test.setDomain("itheima356.com");
        response.addCookie(test);


        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                System.out.println(cookie);
            }
        }


        String username = request.getParameter("username");
        System.out.println("前端传参:"+username);
        response.getWriter().print("{\"username\":\"xiaoming\"}");
    }
}
