package com.itheima.web.servlet;

import com.alipay.api.AlipayApiException;
import com.itheima.constants.Global;
import com.itheima.domain.*;
import com.itheima.service.OrderService;
import com.itheima.utils.AlipayUtil;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.IDUtil;
import com.itheima.utils.RRHolder;
import com.itheima.web.security.Secured;
import com.itheima.web.vo.Result;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@WebServlet("/order")

public class OrderServlet extends BaseServlet {
    private OrderService orderService= BeanFactory.getBean(OrderService.class);
    @Secured
    public void generate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先判断是否登录
        User user = (User) request.getSession().getAttribute("user");

        //购物车不能为空
        Cart myCart = getMyCart();
        if(myCart.getItems().size()==0){
            writeJson(Result.fail("购物车空空如也....."));
            return;
        }


        //去吧购物车信息 取出来 存到数据库的订单表和订单项表中
        //创建订单
        Order order = new Order();
        order.setTotal(myCart.getTotal());
        order.setState(Global.ORDER_STATE_WEIFUKUAN);
        order.setOrdertime(new Date());
        order.setUid(user.getUid());
        String oid = IDUtil.randId();
        order.setOid(oid);

        //创建订单项集合
        List<OrderItem> orderItems = new ArrayList<>();

        Collection<CartItem> cartItems = myCart.getItems();

        for (CartItem cartItem : cartItems) {
            //一个个的购物项 转换成订单项 为存储到数据库做准备
            OrderItem orderItem = new OrderItem();
            orderItem.setPid(cartItem.getProduct().getPid());
            orderItem.setOid(oid);
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubTotal(cartItem.getSubtotal());

            orderItems.add(orderItem);
        }

        orderService.save(order,orderItems);



        //告诉他成功了
        writeJson(Result.success(oid));


    }
    @Secured
    public void myorders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取当前登录人
        User user = (User) request.getSession().getAttribute("user");

        int pageNumber=1;
        try {
            pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
        }catch (Exception e){}
        int pageSize=2;



        //继续走 分页查询某个人的订单
        List<Order> orders=orderService.findMyOrdersWithItemVos(user.getUid(),pageNumber,pageSize);

        int total=orderService.countByUid(user.getUid());

        //构建pageBean
        PageBean<Order> pageBean = new PageBean<>();

        pageBean.setData(orders);
        pageBean.setPageNumber(pageNumber);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);


        writeJson(Result.success(pageBean));



    }

    public void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取oid
        String oid = request.getParameter("oid");

        //调用service 查询订单
        //Order order=orderService.findById(oid);
        Order order=orderService.findByIdWithItemVos(oid);


        //返回了
        writeJson(Result.success(order));



    }
    public void topay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException, AlipayApiException {


        //先吧参数 更新到数据库
        Map<String, String[]> map = request.getParameterMap();
        Order order = new Order();
        BeanUtils.populate(order,map);

        orderService.updateSHR(order);

        Order byId = orderService.findById(order.getOid());

        //走第三方平台了
        String s = AlipayUtil.generateAlipayTradePagePayRequestForm(order.getOid(), "订单编号:" + order.getOid(), byId.getTotal());

        response.getWriter().print(s);

    }

    public void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //第一件事  验证数据有木有被篡改
        boolean check = AlipayUtil.check(request.getParameterMap());

        if (check){
            String orderId = request.getParameter("out_trade_no");

            orderService.updateState(orderId,Global.ORDER_STATE_YIFUKUAN);

            response.sendRedirect("http://www.itheima356.com:8020/heima356/view/order/info.html?oid="+orderId);
        }else{
            response.getWriter().print("非法的访问");
        }





    }
    public void notify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //第一件事  验证数据有木有被篡改
        boolean check = AlipayUtil.check(request.getParameterMap());

        if (check){
            String orderId = request.getParameter("out_trade_no");

            orderService.updateState(orderId,Global.ORDER_STATE_YIFUKUAN);

            response.getWriter().print("success");
        }else{
            response.getWriter().print("非法的访问");
        }


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
