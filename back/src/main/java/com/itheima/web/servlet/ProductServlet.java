package com.itheima.web.servlet;

import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends BaseServlet {
    private ProductService productService= BeanFactory.getBean(ProductService.class);
    private CategoryService categoryService= BeanFactory.getBean(CategoryService.class);
    public void hotsandnews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //查询最新商品
        List<Product> news=productService.findNews();

        //查询热门的商品
        List<Product> hots=productService.findHots();

        //先整一个容器 放起来
        HashMap<Object, Object> map = new HashMap<>();
        map.put("hots",hots);
        map.put("news",news);



        //返回

        writeJson(Result.success(map));




    }

    public void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取pid
        String pid = request.getParameter("pid");
        //调用service查询
        Product product=productService.findById(pid);

        //还需要category对象
        Category category=categoryService.findById(product.getCid());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("product",product);
        map.put("category",category);

        //返回
        writeJson(Result.success(map));

    }
    public void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //必然 cid  pageNumber
        String cid = request.getParameter("cid");
        int pageNumber =Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize=2;

        //调用service查询
        List<Product> products=productService.findPage(cid,pageNumber,pageSize);

        //调用service 查询总个数
        int total=productService.countByCid(cid);

        PageBean<Product> pageBean = new PageBean<>();
        pageBean.setPageNumber(pageNumber);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        pageBean.setData(products);

        //返回
        writeJson(Result.success(pageBean));

    }
    public void page1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        int pageNumber =Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize=10;

        //调用service查询
        List<Product> products=productService.findPage(pageNumber,pageSize);

        //调用service 查询总个数
        int total=productService.count();

        PageBean<Product> pageBean = new PageBean<>();
        pageBean.setPageNumber(pageNumber);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(total);
        pageBean.setData(products);

        //返回
        writeJson(Result.success(pageBean));

    }
}
