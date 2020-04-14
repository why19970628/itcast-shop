package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.IDUtil;
import com.itheima.utils.UploadUtil;
import com.itheima.web.vo.Result;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@WebServlet("/productadd")
public class ProductAddServlet extends HttpServlet {
    private ProductService productService= BeanFactory.getBean(ProductService.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取所有参数
        Map<String, String[]> map = UploadUtil.parse(request);

        //封装对象
        Product product = new Product();

        try {
            BeanUtils.populate(product,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //还有参数没封装
        product.setPid(IDUtil.randId());
        product.setPdate(new Date());

        //调用service 保存
        productService.add(product);
        //返回成功信息
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = Result.success();
        String s = objectMapper.writeValueAsString(result);
        response.getWriter().print(s);
    }
}
