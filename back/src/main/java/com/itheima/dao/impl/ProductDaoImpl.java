package com.itheima.dao.impl;

import com.itheima.constants.Global;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<Product> findNews() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from product order by pdate desc limit 0,9";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Product.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findHots() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from product where is_hot=? limit 0,9";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Product.class), Global.PRODUCT_IS_HOT);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product findById(String pid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from product where pid=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(Product.class),pid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findPage(String cid, int pageNumber, int pageSize) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from product where cid=? limit ?,?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Product.class),cid,(pageNumber-1)*pageSize,pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByCid(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select count(*) from product where cid=? ";
        try {
            return ((Long)queryRunner.query(sql,new ScalarHandler(),cid)).intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());
        /**
         *   private String pid;//id
         *     private String pname;
         *     private Double market_price;
         *
         *     private Double shop_price;
         *     private String pimage;
         *     private Date pdate;
         *
         *     private Integer is_hot;
         *     private String pdesc;
         *     private Integer pflag;
         *     private String cid;
         */
        String sql="insert into product  values(?,?,?,?,?,?,?,?,?,?) ";
        try {
            queryRunner.update(sql,
                    product.getPid(),product.getPname(),product.getMarket_price(),
                    product.getShop_price(),product.getPimage(),product.getPdate(),
                    product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findPage(int pageNumber, int pageSize) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from product  limit ?,?";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Product.class),(pageNumber-1)*pageSize,pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select count(*) from product  ";
        try {
            return ((Long)queryRunner.query(sql,new ScalarHandler())).intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
