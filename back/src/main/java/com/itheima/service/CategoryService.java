package com.itheima.service;

import com.itheima.domain.Category;
import com.itheima.exceptions.CategoryHasProductException;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(String cid);

    void add(Category category);

    void update(Category category);

    void del(String cid) throws CategoryHasProductException;
}
