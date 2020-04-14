package com.itheima.dao.impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.OrderItemVo;
import com.itheima.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void saveOrder(Order order) {
        QueryRunner queryRunner = new QueryRunner();
        /**
         * private String oid;
         *
         *     private Date ordertime;
         *
         *     private Double total;
         *
         *     private Integer state;//0 未付款 1 已付款 2....
         *
         *     private String address;
         *
         *     private String name;
         *
         *     private String telephone;
         *     private String uid;
         */
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        try {
            queryRunner.update(DataSourceUtil.getConnection(),sql,
                    order.getOid(),order.getOrdertime(),order.getTotal(),
                    order.getState(),order.getAddress(),order.getName(),
                    order.getTelephone(),order.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveItem(OrderItem orderItem) {
        QueryRunner queryRunner = new QueryRunner();

        String sql="insert into orderitem values(?,?,?,?)";
        try {
            queryRunner.update(DataSourceUtil.getConnection(),sql,
                    orderItem.getCount(),orderItem.getSubTotal(),
                    orderItem.getPid(), orderItem.getOid()
                    );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findById(String oid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from orders where oid=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(Order.class),oid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderItemVo> findItemVos(String oid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select oi.count,oi.subtotal,oi.pid,p.pname ,p.shop_price as price ,p.pimage from orderitem oi,product p where oi.pid=p.pid and oid=?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(OrderItemVo.class),oid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findMyOrders(String uid, int pageNumber, int pageSize) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());
        String sql="select * from orders where uid=? limit ?,?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Order.class),uid,(pageNumber-1)*pageSize,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUid(String uid) {

        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());
        String sql="select count(*) from orders where uid=? ";
        try {
            return ((Long)queryRunner.query(sql,new ScalarHandler(),uid)).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSHR(Order order) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());
        String sql="update orders set address=?,name=?,telephone=? where oid=?";
        try {
            queryRunner.update(sql,order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateState(String orderId, int state) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());
        String sql="update orders set state=? where oid=?";
        try {
            queryRunner.update(sql,state,orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
