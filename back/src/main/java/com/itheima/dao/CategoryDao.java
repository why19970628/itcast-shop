package com.itheima.dao;

import com.itheima.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    Category findById(String cid);

    void save(Category category);

    void update(Category category);

    void del(String cid);

    long existProduct(String cid);
}
