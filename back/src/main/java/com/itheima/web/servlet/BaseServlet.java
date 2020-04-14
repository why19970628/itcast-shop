package com.itheima.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.User;
import com.itheima.utils.RRHolder;
import com.itheima.web.security.Secured;
import com.itheima.web.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String md = request.getParameter("md");

        //md其实就是该servlet 对应的方法名
        //getMethod 传参
        //第一个参数 方法的名字
        //第二组参数 该方法的形参列表
        try {
            Method method = this.getClass().getMethod(md, HttpServletRequest.class, HttpServletResponse.class);
            //第一个参数传 对象
            //第二组参数 实参


            //判断一下 该方法上是否加上了 xxx标识实际就是注解

            boolean present = method.isAnnotationPresent(Secured.class);
            User user=null;
            if (present){
                //说明你需要登录
                user = (User) request.getSession().getAttribute("user");
                if (user==null){
                    writeJson(Result.needlogin());
                    return;
                }
                //走到这 说明啥 肯定是登录状态
                String role = user.getRemark();

                Secured annotation = method.getAnnotation(Secured.class);
                String value = annotation.value();
                if (!role.equals(value)){

                    writeJson(Result.authFail());
                    return;
                }
            }



            method.invoke(this,request,response);


        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("非法的请求");
        }

    }
    public void writeJson(Object result) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(result);
        //取出来
        HttpServletResponse response = RRHolder.RESPONSE_THREADLOCAL.get();
        response.getWriter().print(s);

    }

}
