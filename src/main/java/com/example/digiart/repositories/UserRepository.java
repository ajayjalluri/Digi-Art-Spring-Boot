package com.example.digiart.repositories;

import com.example.digiart.entities.Art;
import com.example.digiart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(
            value = "SELECT * FROM user ORDER BY no_followers DESC LIMIT 10",
            nativeQuery = true)
    Collection<User> topArtist();
    Optional<User> findByUserName(String userName);

    @Query(
            value = "SELECT username FROM user ",nativeQuery = true)
    List<String> usernames();
}

