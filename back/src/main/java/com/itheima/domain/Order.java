package com.itheima.domain;

import java.util.Date;
import java.util.List;

public class Order {

    private String oid;

    private Date ordertime;

    private Double total;

    private Integer state;//0 未付款 1 已付款 2....

    private String address;

    private String name;

    private String telephone;
    private String uid;
    private List<OrderItemVo> vos;

    public List<OrderItemVo> getVos() {
        return vos;
    }

    public void setVos(List<OrderItemVo> vos) {
        this.vos = vos;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
