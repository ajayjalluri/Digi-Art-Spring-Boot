package com.example.digiart.controllers;

import com.example.digiart.entities.Art;
import com.example.digiart.entities.Color;
import com.example.digiart.entities.Product;
import com.example.digiart.entities.User;
import com.example.digiart.exceptions.IdNotFoundException;
import com.example.digiart.repositories.ArtRepository;
import com.example.digiart.repositories.ColorRepository;
import com.example.digiart.repositories.UserRepository;
import com.example.digiart.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class ArtController {

    @Autowired
    private ArtRepository artRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private JwtUtil jwtUtil;
//    @Autowired
//    private Gcp gcp;


    @RequestMapping(value = "/artist/addArt/", method = RequestMethod.POST)
    public String addArtImage(@RequestParam("image") MultipartFile multipartFile, @RequestHeader("Authorization") String token, @RequestParam("artName") String artName, @RequestParam("artDescription") String artDescription) throws IOException {
        User user = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get();
        Art art = new Art();
        art.setArtName(artName);
        art.setArtDescription(artDescription);
        art.setUserID(user.getUserId());

        int a = user.getArts().size();
        String filepath =  "arts" + user.getUserId().toString() + a;
        art.setArtLocation(filepath);

        Gcp gcp = new Gcp();
        gcp.uploading(multipartFile,filepath);


        user.setArts(art);
        userRepository.save(user);
        return "File created";
    }

    @RequestMapping(value = "/art/getArt/{id}", method = RequestMethod.GET)
    public ArtAndUser getArts(@PathVariable("id") Integer Id) {
        if (artRepository.existsById(Id)) {
            Art art = artRepository.findById(Id).get();
            Integer userid = art.getUserID();
            ArtAndUser au = new ArtAndUser();
            Gcp gcp = new Gcp();
            art.setArtImage(gcp.downloading(art.getArtLocation()));
            au.setArt(art);





            User user = userRepository.findById(userid).get();
            au.setArtistBio(user.getBio());
            au.setArtistName(user.getUserName());
            if(user.getProfilePicLocation()!=null){
                au.setArtistProfilePic(gcp.downloading(user.getProfilePicLocation()));
            }

            List <Product> dummyList = art.getProducts();

            List <Integer> a = new ArrayList<>();
            Iterator<Product> dummyIterator = dummyList.iterator();
            while(dummyIterator.hasNext()) {
                Product ele = dummyIterator.next();
                a.add(ele.getProductId());
            }

            Iterator<Integer> aIterator = a.iterator();
            while(aIterator.hasNext()) {
                List<Colorproductpage> cs1 = new ArrayList<>();
                List<ColorUtil> cs2 = colorRepository.colors(aIterator.next());

                Iterator<ColorUtil> csiterator  =  cs2.iterator();
                while(csiterator.hasNext()){
                    ColorUtil c2 = csiterator.next();
                    Colorproductpage c1 = new Colorproductpage();
                    c1.setpId(c2.getp_id());
                    c1.setId(c2.getid());
                    c1.setProductPicLocation(c2.getproduct_pic_location());
                    if(c2.getproduct_pic_location()!=null)
                    {
                        c1.setProduct_img(gcp.downloading(c2.getproduct_pic_location()));
                    }
                    else{
                        c1.setProduct_img("");
                    }

                    c1.setColor(c2.getcolor());
                    cs1.add(c1);
                }
                au.setColors(cs1);
            }

            return au;

        } else throw new IdNotFoundException("User: " + Id + " does Not Exist.!");

    }


}
