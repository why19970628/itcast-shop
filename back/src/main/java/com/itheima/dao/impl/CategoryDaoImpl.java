package com.itheima.dao.impl;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAll() {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from category";
        try {
            return queryRunner.query(sql,new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category findById(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from category where cid=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(Category.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Category category) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="insert into category values(?,?)";
        try {
            queryRunner.update(sql,category.getCid(),category.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category category) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="update  category set cname=? where cid=?";
        try {
            queryRunner.update(sql,category.getCname(),category.getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void del(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="delete from  category where cid=?";
        try {
            queryRunner.update(sql,cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long existProduct(String cid) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select count(*) from product where cid=?";
        try {
            return (long) queryRunner.query(sql,new ScalarHandler(),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
