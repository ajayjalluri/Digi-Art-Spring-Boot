package com.example.digiart.controllers;

import com.example.digiart.entities.User;
import com.example.digiart.exceptions.IdNotFoundException;
import com.example.digiart.repositories.ArtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class LikeController {
//    @Autowired
//    ArtRepository artRepository;
//
//    @RequestMapping(value = "/like/art/{id}", method = RequestMethod.GET)
//    public String getLikes(@PathVariable("id") Integer Id) {
//
//        if (artRepository.existsById(Id)) {
//            User user = artRepository.findById(Id).get();
//        } else throw new IdNotFoundException("User: " + Id + " does Not Exist.!");
//        return "Added like to the art";
//
//    }
}
