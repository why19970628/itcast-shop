package com.itheima.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.exceptions.CategoryHasProductException;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao= BeanFactory.getBean(CategoryDao.class);
    @Override
    public List<Category> findAll() {
        //连上redis  把分类信息 放入redis中
        Jedis connection = null;

        try {
            connection = RedisUtil.getConnection();

            String categories = connection.get("heima356_categories");

            ObjectMapper objectMapper = new ObjectMapper();
            List list = objectMapper.readValue(categories, List.class);
            return list;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (connection!=null){
                connection.close();
            }
        }
    }

    @Override
    public Category findById(String cid) {
        return categoryDao.findById(cid);
    }

    @Override
    public void add(Category category) {
        categoryDao.save(category);
        //此时缓存已经不是最新的数据
        //更新一份最新放入的缓存中
        refreshCategoryCache();

    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
        //此时缓存已经不是最新的数据
        //更新一份最新放入的缓存中
        refreshCategoryCache();
    }

    @Override
    public void del(String cid) throws CategoryHasProductException {
        //查看该分类下是否存在商品
        long count=categoryDao.existProduct(cid);
        if(count>0){
            //如果有 就是不让删除
            throw new CategoryHasProductException();

        }else{
            //如果没有 该走删除逻辑

            categoryDao.del(cid);
            //此时缓存已经不是最新的数据
            //更新一份最新放入的缓存中
            refreshCategoryCache();
        }
    }

    private void refreshCategoryCache() {

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
            throw new RuntimeException(e);
        }finally {
            if (connection!=null){
                connection.close();
            }
        }

    }
}
