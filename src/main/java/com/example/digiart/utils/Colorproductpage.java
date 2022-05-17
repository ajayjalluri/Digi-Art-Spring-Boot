package com.example.digiart.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Transient;

public class Colorproductpage {

    private Integer Id;

    private String color;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getProductPicLocation() {
        return productPicLocation;
    }

    public void setProductPicLocation(String productPicLocation) {
        this.productPicLocation = productPicLocation;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String productPicLocation;

    private Integer pId;

    private String product_img;
}
