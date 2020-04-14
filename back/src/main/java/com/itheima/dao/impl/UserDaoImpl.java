package com.itheima.dao.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;


public class UserDaoImpl implements UserDao {

    @Override
    public void save(User user) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());
        /**
         *  private String uid;
         *     private String username;
         *     private String password;
         *
         *     private String name;
         *     private String email;
         *     private String birthday;
         *
         *     private String gender;
         *     private String remark="1";//预留字段 留作扩展权限内容
         */
        String sql="insert into user values(?,?,?,?,?,?,?,?)";
        try {
            queryRunner.update(sql,
                    user.getUid(),user.getUsername(),user.getPassword(),
                    user.getName(),user.getEmail(),user.getBirthday(),user.getGender(),user.getRemark());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUser(String username, String password) {
        QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDatasource());

        String sql="select * from user where username=? and password=?";
        try {
            return queryRunner.query(sql,new BeanHandler<>(User.class),username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
