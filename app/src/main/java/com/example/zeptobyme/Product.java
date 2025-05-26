package com.example.zeptobyme;


import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int imageResId;
    private String price;
    private String mrp;
    private String quantity;
    private String desc;



    public Product(String name, int imageResId, String price, String mrp, String quantity, String desc){
        this.name=name;
        this.imageResId=imageResId;
        this.price=price;
        this.mrp=mrp;
        this.quantity=quantity;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getPrice() {
        return price;
    }

    public String getMrp() {
        return mrp;
    }

    public String getQuantity() {
        return quantity;
    }
}
