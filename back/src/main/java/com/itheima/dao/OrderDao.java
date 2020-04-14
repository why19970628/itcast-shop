package com.itheima.dao;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.OrderItemVo;

import java.util.List;

public interface OrderDao {
    void saveOrder(Order order);

    void saveItem(OrderItem orderItem);

    Order findById(String oid);

    List<OrderItemVo> findItemVos(String oid);

    List<Order> findMyOrders(String uid, int pageNumber, int pageSize);

    int countByUid(String uid);

    void updateSHR(Order order);

    void updateState(String orderId, int state);
}
