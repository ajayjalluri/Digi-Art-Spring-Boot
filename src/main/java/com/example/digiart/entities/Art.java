package com.example.digiart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer artId;

    private String artName;

    @Column(length = 200)
    private String artDescription;

    private String likes;
    private String artLocation;

    private String qtypurchased ;

    public String getQtypurchased() {
        return qtypurchased;
    }

    public void setQtypurchased(String qtypurchased) {
        if(this.qtypurchased==null){
            this.qtypurchased = "0";
        }
        Integer n = Integer.parseInt(this.qtypurchased)+Integer.parseInt(qtypurchased);

        this.qtypurchased = n.toString();
    }

    @JsonInclude()
    @Transient
    private String artImage = "";
    public String getArtImage() {
        return artImage;
    }

    public void setArtImage(String artImage) {
        this.artImage = artImage;
    }

    @JsonIgnore
    @ManyToMany(mappedBy="likedArts", cascade = CascadeType.ALL)
    private Set<User> likedByUsers= new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "art_id",referencedColumnName = "artId")
    List<Product> products = new ArrayList<>();


    private Integer userID;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getArtLocation() {
        return artLocation;
    }

    public void setArtLocation(String artLocation) {
        this.artLocation = artLocation;
    }

    public String getArtDescription() {
        return artDescription;
    }

    public void setArtDescription(String artDescription) {
        this.artDescription = artDescription;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(Product product) {
        this.products.add(product);
    }
}
