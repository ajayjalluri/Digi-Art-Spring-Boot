package com.example.digiart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true)
    private String userName;

    private String password;
    private String Roles = "User";

    private String bio;
    private String email;
    private String phonenumber;
    private String address;
    private String profilePicLocation;

    @JsonInclude()
    @Transient
    private String profile_img = "";

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    private long noFollowers = 0;

    public long getNoFollowers() {
        return noFollowers;
    }

    public void setNoFollowers(long noFollowers) {
        this.noFollowers = noFollowers;
    }

    public void increaseFollowers() {
        this.noFollowers = this.noFollowers + 1;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private List<Art> arts = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="liked_arts",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="art_id")
    )
    private Set<Art> likedArts = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id",referencedColumnName = "cartId")
    Cart carts = new Cart();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    List<Orders> order = new ArrayList<>();

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name="purchased_products",
//            joinColumns = @JoinColumn(name="userp_id"),
//            inverseJoinColumns = @JoinColumn(name="product_id")
//    )
//    private Set<Color> purchasedProducts = new HashSet<>();

    public List<Orders> getOrder() {
        return order;
    }

    public void setOrder(Orders orders) {
        this.order.add(orders);
    }

    public void updateOrder(List<Orders> orders){
        this.order = orders;
    }

//    public Set<Color> getPurchasedProducts() {
//        return purchasedProducts;
//    }
//
//    public void setPurchasedProducts(Color purchasedProduct) {
//        this.purchasedProducts.add(purchasedProduct);
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePicLocation() {
        return profilePicLocation;
    }

    public void setProfilePicLocation(String profilePicLocation) {
        this.profilePicLocation = profilePicLocation;
    }


    public void setArts(Art art) {
        this.arts.add(art);
    }


    public List<Art> getArts() {
        return arts;
    }


    public Set<Art> getLikedArts() {
        return likedArts;
    }

    public void setLikedArts(Set<Art> likedArts) {
        this.likedArts = likedArts;
    }

    public Cart getCarts() {
        return carts;
    }

    public void setCarts(Cart carts) {
        this.carts = carts;
    }

    public void updateArts(List<Art>arts){
        this.arts = arts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String roles) {
        Roles = roles;
    }
}
