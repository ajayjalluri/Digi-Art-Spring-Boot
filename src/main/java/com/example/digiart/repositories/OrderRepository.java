package com.example.digiart.repositories;

import com.example.digiart.entities.Orders;
import com.example.digiart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    @Query(
            value = "SELECT producto_id FROM orders GROUP BY producto_id ORDER BY COUNT(producto_id) DESC LIMIT 10",
            nativeQuery = true)
    List<Integer> topOrders();

    @Query(
            value = "SELECT ",
            nativeQuery = true)
    List<Integer>  usersellings(Integer userid);
}