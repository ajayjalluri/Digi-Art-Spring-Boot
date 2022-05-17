package com.example.digiart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String itemName;
    private String description;
    private String price;
    private String productheading;
    private String productPicLocation;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id",referencedColumnName = "productId")
//    List<Color> colors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",referencedColumnName = "productId")
    List<Size> sizes = new ArrayList<>();

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "products")
//    private Set<Cart> carts = new HashSet<>();

//    @JsonIgnore
//    @ManyToMany(mappedBy="purchasedProducts", cascade = CascadeType.ALL)
//    private Set<User> purchasedUsers= new HashSet<>();

//    public Set<User> getPurchasedUsers() {
//        return purchasedUsers;
//    }
//
//    public void setPurchasedUsers(Set<User> purchasedUsers) {
//        this.purchasedUsers = purchasedUsers;
//    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductheading() {
        return productheading;
    }

    public void setProductheading(String productheading) {
        this.productheading = productheading;
    }

    public String getProductPicLocation() {
        return productPicLocation;
    }

    public void setProductPicLocation(String productPicLocation) {
        this.productPicLocation = productPicLocation;
    }

//    public List<Color> getColors() {
//        return colors;
//    }
//
//    public void setColors(Color color) {
//        this.colors.add(color);
//    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(Size size) {
        this.sizes.add(size);
    }

//    public Set<Cart> getCarts() {
//        return carts;
//    }
//
//    public void setCarts(Cart cart) {
//        this.carts.add(cart);
//    }
}
