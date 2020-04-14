package com.itheima.web.servlet;

import com.itheima.web.security.Secured;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends BaseServlet {
    @Secured("admin")
    public void x1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("我是方法x1,而且还必须是管理员");



    }
    @Secured
    public void x2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("我是方法x2,随便都行");


    }
}
