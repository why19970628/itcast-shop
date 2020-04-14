package com.itheima.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao= BeanFactory.getBean(ProductDao.class);
    @Override
    public List<Product> findNews() {

        Jedis connection = null;

        try {
            connection = RedisUtil.getConnection();

            //先去缓存查询 商品信息
            String news = connection.get("heima356_news");
            //如果没有
            if(news==null){
                List<Product> productList = productDao.findNews();

                //加入缓存中
                String s = new ObjectMapper().writeValueAsString(productList);

                connection.set("heima356_news",s);
                connection.expire("heima356_news",10);
                return productList;

            }else{

                //如果有
               return  new ObjectMapper().readValue(news,List.class);

            }

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
    public List<Product> findHots() {
        return productDao.findHots();
    }

    @Override
    public Product findById(String pid) {
        return productDao.findById(pid);
    }

    @Override
    public List<Product> findPage(String cid, int pageNumber, int pageSize) {
        return productDao.findPage(cid,pageNumber,pageSize);
    }

    @Override
    public int countByCid(String cid) {
        return productDao.countByCid(cid);
    }

    @Override
    public void add(Product product) {
        productDao.save(product);
    }

    @Override
    public List<Product> findPage(int pageNumber, int pageSize) {
        return productDao.findPage(pageNumber,pageSize);

    }

    @Override
    public int count() {
        return productDao.count();
    }
}
