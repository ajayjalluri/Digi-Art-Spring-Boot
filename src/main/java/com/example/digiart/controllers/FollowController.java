package com.example.digiart.controllers;

import com.example.digiart.entities.Follow;
import com.example.digiart.entities.User;
import com.example.digiart.repositories.FollowRepository;
import com.example.digiart.repositories.UserRepository;
import com.example.digiart.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class FollowController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/follow/addConnection/{idFollowee}/", method = RequestMethod.GET)
    public String addFollower(@PathVariable Integer idFollowee, @RequestHeader("Authorization") String token) {
        User user = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get();
        Follow follow = new Follow();
        follow.setFollowerId(user.getUserId());
        follow.setFolloweeId(idFollowee);

        followRepository.save(follow);

        System.out.println(user);
        user.increaseFollowers();
        userRepository.save(user);
        return "Connection created!";

    }
}
