package com.uwplp.components.models;


import java.util.Date;

public class OrderModel {
    private Long order_id;
    private Long user_id;
    private Long product_id;
    private Long order_price;
    private Long order_size;
    private Date order_time;

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Long order_price) {
        this.order_price = order_price;
    }

    public Long getOrder_size() {
        return order_size;
    }

    public void setOrder_size(Long order_size) {
        this.order_size = order_size;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "order_id=" + order_id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", order_price=" + order_price +
                ", order_size=" + order_size +
                ", order_time=" + order_time +
                '}';
    }
}
