package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.constants.Global;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.IDUtil;
import com.itheima.utils.MD5Util;
import com.itheima.web.vo.Result;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
    private UserService userService= BeanFactory.getBean(UserService.class);
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取账号密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String encodeWithSalt = MD5Util.encodeWithSalt(username, password);

        //调用service 查询用户
        User user=userService.findUser(username,encodeWithSalt);
        if (user==null){
            //失败返回账号密码错误

            Result result = Result.fail("账号密码错误");
            //转换成json格式
            writeJson(result);

        }else{
            //找到了 返回登录成功
            //保存用户登录状态
            request.getSession().setAttribute("user",user);


            Result result = Result.success();
            writeJson(result);
        }



    }

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受请求
        //校验数据格式是否正确
        HashMap<Object, Object> errors = new HashMap<>();


        String username = request.getParameter("username");

        //规定 用户名字 必须在4-8字符
        if(username==null||username.trim().length()<4||username.trim().length()>8){
            //用户名格式不对了
            errors.put("username","用户名长度不正确");

        }
        String password = request.getParameter("password");

        //规定 用户名字 必须在4-8字符
        if(password==null||password.trim().length()<8||password.trim().length()>12){
            //用户名格式不对了
            errors.put("password","密码长度不正确");

        }
        if (errors.size()>0){
            //如果不正确  直接返回了

            Result result = Result.fail(errors);
            //转换成json格式
            writeJson(result);


        }else{
            //格式ok
            //封装对象 调用service 保存
            Map<String, String[]> map = request.getParameterMap();

            User user = new User();

            try {
                BeanUtils.populate(user,map);
            } catch (Exception e) {
            }
            user.setUid(IDUtil.randId());
            //这时候封装user password 明文
            String mingPassword = user.getPassword();
            String encodeWithSalt = MD5Util.encodeWithSalt(user.getUsername(), mingPassword);
            user.setPassword(encodeWithSalt);

            userService.regist(user);

            Result result = Result.success();
            writeJson(result);


            //返回成功信息
        }

    }
    public void getMyName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            Result result = Result.fail();
            writeJson(result);
        }else{
            Result result = Result.success(user.getName());
            //转换成json格式
            writeJson(result);
        }
    }
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        Result result = Result.success();
        writeJson(result);
    }
}
