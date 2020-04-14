package com.itheima.demo;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

public class PageBeanDemo {
    public static void main(String[] args) {
        PageBean<Product> pageBean = new PageBean<>();
        pageBean.setPageNumber(74);
        pageBean.setPageSize(10);
        pageBean.setTotal(760);

        System.out.println(pageBean.getTotalPage());

        System.out.println(pageBean.getStart());
        System.out.println(pageBean.getEnd());


    }
}
