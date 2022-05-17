package com.example.digiart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String color;

    private String productPicLocation;

    private Integer pId;


//    @JsonInclude()
//    @Transient
//    private String product_img;
//
//    public String getProduct_img() {
//        return product_img;
//    }
//
//    public void setProduct_img(String product_img) {
//        this.product_img = product_img;
//    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private Product product;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "products")
//    private Set<Cart> carts = new HashSet<>();

//        public Set<Cart> getCarts() {
//        return carts;
//    }
//
//    public void setCarts(Cart cart) {
//        this.carts.add(cart);
//    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductPicLocation() {
        return productPicLocation;
    }

    public void setProductPicLocation(String productPicLocation) {
        this.productPicLocation = productPicLocation;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
