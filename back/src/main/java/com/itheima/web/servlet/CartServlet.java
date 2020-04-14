package com.itheima.web.servlet;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.RRHolder;
import com.itheima.web.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/cart")
public class CartServlet extends BaseServlet {
    private ProductService productService= BeanFactory.getBean(ProductService.class);
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //提交过来 两个参数 pid 和count
        String pid = request.getParameter("pid");
        int count = Integer.parseInt(request.getParameter("count"));

        //做库存判断 简单 直接count>10 直接告诉库存不足
        if(count>10){
            writeJson(Result.fail("库存不足"));
            return;
        }

        //将添加到自己的购物车中
        Product product=productService.findById(pid);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCount(count);

        Cart cart = getMyCart();
        cart.add(cartItem);

        //返回成功
        writeJson(Result.success());


    }
    public void mycart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取自己的车
        Cart myCart = getMyCart();

        writeJson(Result.success(myCart));


    }


    public void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取pid
        String pid = request.getParameter("pid");
        //获取自己的车 删除
        getMyCart().remove(pid);
        //返回成功信息
        writeJson(Result.success());


    }
    public void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取自己车车 清空
        getMyCart().clear();
        //返回
        writeJson(Result.success());
    }
    private Cart getMyCart() {
        //在session中
        HttpServletRequest request = RRHolder.REQUEST_THREADLOCAL.get();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart==null){
            cart=new Cart();
            //放入session中
            session.setAttribute("cart",cart);
        }
        return cart;
    }
}
