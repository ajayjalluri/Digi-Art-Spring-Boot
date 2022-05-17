package com.example.digiart.repositories;

import com.example.digiart.entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes,Integer> {
}
