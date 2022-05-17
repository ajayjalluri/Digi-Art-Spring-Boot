package com.example.digiart.repositories;

import com.example.digiart.entities.Art;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ArtRepository extends JpaRepository<Art,Integer> {
    @Query(
            value = "SELECT * FROM art ORDER BY art_id DESC LIMIT 10",
            nativeQuery = true)
    Collection<Art> lastTen();





}
