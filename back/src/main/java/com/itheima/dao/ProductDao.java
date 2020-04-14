package com.itheima.dao;

import com.itheima.domain.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findNews();

    List<Product> findHots();

    Product findById(String pid);

    List<Product> findPage(String cid, int pageNumber, int pageSize);

    int countByCid(String cid);

    void save(Product product);

    List<Product> findPage(int pageNumber, int pageSize);

    int count();
}
