package com.itheima.web.servlet;

import com.itheima.domain.Category;
import com.itheima.exceptions.CategoryHasProductException;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.IDUtil;
import com.itheima.web.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService= BeanFactory.getBean(CategoryService.class);
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用service查询分类列表
        List<Category> categories= categoryService.findAll();
        //写回去

        writeJson(Result.success(categories));
    }
    public void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取提交的cid
        String cid = request.getParameter("cid");

        Category category = categoryService.findById(cid);


        //返回成功信息
        writeJson(Result.success(category));



    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取提交的cname
        String cname = request.getParameter("cname");
        //验证格式
        if(cname==null||cname.trim().length()<4||cname.trim().length()>8){
            writeJson(Result.fail("格式不正确长度应该在4~8之间"));
            return;
        }
        Category category = new Category();
        category.setCname(cname);
        category.setCid(IDUtil.randId());
        //没有问题 就插入数据库
        categoryService.add(category);

        //返回成功信息
        writeJson(Result.success());




    }
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取提交的cname
        String cname = request.getParameter("cname");
        //验证格式
        if(cname==null||cname.trim().length()<4||cname.trim().length()>8){
            writeJson(Result.fail("格式不正确长度应该在4~8之间"));
            return;
        }
        String cid = request.getParameter("cid");
        Category category = new Category();
        category.setCname(cname);
        category.setCid(cid);
        //没有问题 就插入数据库
        categoryService.update(category);

        //返回成功信息
        writeJson(Result.success());
    }
    public void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取提交的cid
        String cid = request.getParameter("cid");

        try {
            categoryService.del(cid);
            //返回成功信息
            writeJson(Result.success());
        } catch (CategoryHasProductException e) {
            writeJson(Result.fail("该分类下存在商品无法删除"));
        }


    }
}
