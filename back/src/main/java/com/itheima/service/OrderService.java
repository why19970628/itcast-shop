package com.itheima.service;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;

import java.util.List;

public interface OrderService {
    void save(Order order, List<OrderItem> orderItems);

    Order findById(String oid);

    Order findByIdWithItemVos(String oid);

    List<Order> findMyOrdersWithItemVos(String uid, int pageNumber, int pageSize);

    int countByUid(String uid);

    void updateSHR(Order order);

    void updateState(String orderId, int orderStateYifukuan);
}
