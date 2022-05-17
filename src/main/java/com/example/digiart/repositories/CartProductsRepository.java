package com.example.digiart.repositories;

import com.example.digiart.entities.CartProductsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartProductsRepository extends JpaRepository<CartProductsInfo,Integer> {

    @Query(
            value = "SELECT * FROM cart_products_info WHERE cart_id = ?1 AND product_id=?2 AND size =?3",
            nativeQuery = true)
    Optional<CartProductsInfo> cartproductinfoid(Integer cart_id, Integer product_id, String size);
}
