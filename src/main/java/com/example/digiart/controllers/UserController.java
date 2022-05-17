package com.example.digiart.controllers;


import com.example.digiart.entities.*;
import com.example.digiart.repositories.ColorRepository;
import com.example.digiart.repositories.ProductRepository;
import com.example.digiart.repositories.UserRepository;
import com.example.digiart.exceptions.IdNotFoundException;

import com.example.digiart.utils.FileUploadUtil;
import com.example.digiart.utils.Gcp;
import com.example.digiart.utils.JwtUtil;
import com.google.common.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@RestController()
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        return repository.save(user);

    }


    //other user
    @RequestMapping(value = "/user/otherUser/{id}", method = RequestMethod.GET)
    public User getOtherUsers(@PathVariable("id") Integer Id) {
        if (repository.existsById(Id)) {
            User user = repository.findById(Id).get();

            Gcp gcp = new Gcp();
            if (user.getProfilePicLocation() != null) {
                user.setProfile_img(gcp.downloading(user.getProfilePicLocation()));

            }

            List<Art> arts = user.getArts();
            List<Art> newarts = new ArrayList<>();
            Iterator<Art> artIterator = arts.iterator();

            while (artIterator.hasNext()) {
                Art d = artIterator.next();
                if (d.getArtLocation() != null) {
                    d.setArtImage(gcp.downloading(d.getArtLocation()));
                }
                newarts.add(d);
            }
            user.updateArts(newarts);


            return user;
        } else throw new IdNotFoundException("User: " + Id + " does Not Exist.!");


    }

    @RequestMapping(value = "/user/getUser/", method = RequestMethod.GET)
    public User getUser(@RequestHeader("Authorization") String token) {
        System.out.println(jwtUtil.extractUsername(token.substring(7)));
        repository.findByUserName(jwtUtil.extractUsername(token.substring(7)));
//        if (repository.existsById(Id)) {
//            User user = repository.findById(Id).get();
//        } else throw new IdNotFoundException("User: " + Id + " does Not Exist.!");
//        System.out.println(repository.findById(Id).get());
//        return repository.findById(Id).get();
        User user = repository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get();

        Gcp gcp = new Gcp();
        if (user.getProfilePicLocation() != null) {
            user.setProfile_img(gcp.downloading(user.getProfilePicLocation()));
        }
        List<Art> arts = user.getArts();
        List<Art> newarts = new ArrayList<>();
        Iterator<Art> artIterator = arts.iterator();

        while (artIterator.hasNext()) {
            Art d = artIterator.next();
            if (d.getArtLocation() != null) {
                d.setArtImage(gcp.downloading(d.getArtLocation()));
            }
            newarts.add(d);
        }
        user.updateArts(newarts);


        List<Orders> orders = user.getOrder();
        int size = orders.size();
        List<Orders> orders1 = new ArrayList<>();

        Iterator<Orders> oi = orders.iterator();

        System.out.println(size);
        if (size == 0) {
            return user;
        }

        while (oi.hasNext()) {
            Orders obj = oi.next();
            Integer pid = obj.getProduct_Id();
            Color color = colorRepository.findById(pid).get();
            String loc = color.getProductPicLocation();
            System.out.println(loc);
            if (loc != null) {
                obj.setImage(gcp.downloading(loc));
            }
            Product p = productRepository.findById(color.getpId()).get();

            obj.setProductHeading(p.getProductheading());
            obj.setProductName(p.getItemName());
            orders1.add(obj);
        }
        user.updateOrder(orders1);


        return user;


    }

    @RequestMapping(value = "/user/updateUser", method = RequestMethod.PUT)
    public String updateUser(@RequestBody User user1, @RequestHeader("Authorization") String token) {
        User user2 = repository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get();
        Integer Id = user2.getUserId();
        if (repository.existsById(Id)) {
            user2.setPhonenumber(user1.getPhonenumber());
            user2.setEmail(user1.getEmail());
            user2.setBio(user1.getBio());
            user2.setAddress(user1.getAddress());
            repository.save(user2);
            return "updated";
        } else throw new IdNotFoundException("User: " + Id + " does Not Exist.!");


    }

    @RequestMapping(value = "/user/addImage/", method = RequestMethod.POST)
    public String addUserImage(@RequestParam("image") MultipartFile multipartFile, @RequestHeader("Authorization") String token) throws IOException {
        User user = repository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get();

        String filepath = "user_profile_pics" + user.getUserId().toString();

        Gcp gcp = new Gcp();
        gcp.uploading(multipartFile, filepath);
        user.setProfilePicLocation(filepath);
        repository.save(user);


        return gcp.downloading(filepath);

    }


    @RequestMapping(value = "/user/search/{username}/", method = RequestMethod.GET)
    public String Search(@PathVariable("username") String username) throws IOException {

        Optional<User> user = repository.findByUserName(username);
        if (user.isEmpty()) {
            return "Not found";
        } else {
            User u = user.get();
            Integer i = u.getUserId();
            return i.toString();
        }


    }

    @RequestMapping(value = "/user/usernames/", method = RequestMethod.GET)
    public List<String> usernames() throws IOException {

        List<String> usernames = new ArrayList<>();
        List<User> user = repository.findAll();
        if(user.size() >0)
        {

            Iterator<User> users = user.iterator();
            while(users.hasNext()){
                User u = users.next();
                usernames.add(u.getUserName());
            }
        }

        return usernames;
    }
}

