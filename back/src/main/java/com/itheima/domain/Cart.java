package com.itheima.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 购物车
 */
public class Cart {
    /**
     * key 就是商品 的id
     * value 就是购物项对象
     */
    private Map<String,CartItem> items=new HashMap<>();
    private double total;

    public Collection<CartItem> getItems() {
        return items.values();
    }



    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * 添加购物项
     */
    public void add(CartItem newCartItem){
        //判断 之前是否存在同样商品购物项
        String pid = newCartItem.getProduct().getPid();
        if (items.containsKey(pid)){
            //如果原来就是 只需要将新来的数量添加到原来的里面即可
            CartItem oldCartItem = items.get(pid);
            oldCartItem.setCount(oldCartItem.getCount()+newCartItem.getCount());
        }else{
            items.put(pid,newCartItem);
        }

        //总金额 变
        total+=newCartItem.getSubtotal();

    }

    /**
     * 删除购物项
     */
    public void remove(String pid){
        CartItem remove = items.remove(pid);
        total-=remove.getSubtotal();

    }

    /**
     * 清空购物车
     */
    public void clear(){
        //集合清空
        items.clear();
        //总金额置为0
        total=0.0;
    }
}
