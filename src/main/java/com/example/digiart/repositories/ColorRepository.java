package com.example.digiart.repositories;

import com.example.digiart.entities.Art;
import com.example.digiart.entities.Color;
import com.example.digiart.utils.ColorUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ColorRepository extends JpaRepository<Color,Integer> {
    @Query(
            value = "SELECT id, color, p_id, product_pic_location FROM color WHERE p_id =?1",
            nativeQuery = true)
    List<ColorUtil> colors(Integer p_id);
    @Query(
            value = "SELECT product_id FROM color WHERE id = ?1",
            nativeQuery = true)
    Integer productId(Integer id);
}