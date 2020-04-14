package com.itheima.web.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class CategoryCacheListener implements ServletContextListener {
    private CategoryDao categoryDao= BeanFactory.getBean(CategoryDao.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //在这些代码 吧分类信息 放入缓存中

        //连上redis  把分类信息 放入redis中
        Jedis connection = null;

        try {
            connection = RedisUtil.getConnection();

            List<Category> all = categoryDao.findAll();

            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(all);
            connection.set("heima356_categories",s);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection!=null){
                connection.close();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
