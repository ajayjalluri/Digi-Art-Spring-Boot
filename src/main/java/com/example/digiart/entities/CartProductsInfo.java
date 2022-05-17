package com.example.digiart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
public class CartProductsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer Cart_id;

    public Integer getCart_id() {
        return Cart_id;
    }

    public void setCart_id(Integer cart_id) {
        Cart_id = cart_id;
    }

    private Integer productId;
    private String size;

    private String quantity ;

    private String price;

    @Transient
    @JsonInclude()
    public String image;

    @Transient
    @JsonInclude()
    public String artName;

    @Transient
    @JsonInclude()
    public String artistName;

    @Transient
    @JsonInclude()
    public String Heading;






    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setQuantitytoq(String quantity) {
        Integer n = Integer.parseInt(this.quantity)+Integer.parseInt(quantity);
        this.quantity = n.toString();

    }

    public void decrementQuantity() {
        Integer n = Integer.parseInt(this.quantity)-1;
        this.quantity = n.toString();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }



    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
