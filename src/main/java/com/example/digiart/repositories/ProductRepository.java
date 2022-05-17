package com.example.digiart.repositories;

import com.example.digiart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(
            value = "SELECT art_id FROM product WHERE product_id = ?1",
            nativeQuery = true)
    Integer artId(Integer product_id);
}
