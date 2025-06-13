package com.example.zeptobyme;


import java.io.Serializable;
import java.util.List;

import kotlin.jvm.internal.SerializedIr;


public class Order implements Serializable {
    private String orderId;
    private List<Product> productList;
    private String orderDate;
    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;// e.g., Ordered, Packed, Shipped, Delivered

    public Order(String orderId, List<Product> productList, String orderDate, String status) {
        this.orderId = orderId;
        this.productList = productList;
        this.orderDate = orderDate;
        this.status = status;
        this.title = productList.get(0).getName();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
// Getters and Setters
}
