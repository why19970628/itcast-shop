package com.itheima.service.impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.OrderItemVo;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtil;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao= BeanFactory.getBean(OrderDao.class);
    @Override
    public void save(Order order, List<OrderItem> orderItems) {

        try {
            //开启事务
            DataSourceUtil.beginTransaction();

            //中间业务逻辑start
            orderDao.saveOrder(order);

            for (OrderItem orderItem : orderItems) {
                orderDao.saveItem(orderItem);
            }
            //中间的业务代码 end

            //提交事务
            DataSourceUtil.commitAndClose();
        }catch (Exception e){
            //回滚事务
            DataSourceUtil.rollbackAndClose();
        }






    }

    @Override
    public Order findById(String oid) {
        return orderDao.findById(oid);
    }

    @Override
    public Order findByIdWithItemVos(String oid) {
        Order order = orderDao.findById(oid);
        //只是查询了纯的订单 不够
        List<OrderItemVo> vos= orderDao.findItemVos(oid);

        order.setVos(vos);


        return order;
    }

    @Override
    public List<Order> findMyOrdersWithItemVos(String uid, int pageNumber, int pageSize) {
        List<Order> orders=orderDao.findMyOrders(uid,pageNumber,pageSize);

        //再加工
        for (Order order : orders) {
            List<OrderItemVo> vos= orderDao.findItemVos(order.getOid());

            order.setVos(vos);
        }


        return orders;
    }

    @Override
    public int countByUid(String uid) {

        return orderDao.countByUid(uid);
    }

    @Override
    public void updateSHR(Order order) {
        orderDao.updateSHR(order);
    }

    @Override
    public void updateState(String orderId, int state) {
        orderDao.updateState(orderId,state);
    }
}
