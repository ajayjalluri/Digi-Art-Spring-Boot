package com.example.digiart.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    private Integer totalPrice;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "cart_products",
//            joinColumns =  @JoinColumn(name = "cart_id"),
//            inverseJoinColumns =  @JoinColumn (name = "pc_id"))
//    private Set<Color> products =  new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id",referencedColumnName = "cartId")
    private List<CartProductsInfo> products = new ArrayList<>();

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartProductsInfo> getProducts() {
        return products;
    }

    public void setProducts(CartProductsInfo product) {
        this.products.add(product);
    }

    public void updateProducts(List<CartProductsInfo> products){
        this.products = products;
    }
}
