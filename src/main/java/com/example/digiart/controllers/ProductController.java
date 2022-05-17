package com.example.digiart.controllers;

import com.example.digiart.entities.*;
import com.example.digiart.repositories.ArtRepository;
import com.example.digiart.repositories.ColorRepository;
import com.example.digiart.repositories.ProductRepository;
import com.example.digiart.repositories.UserRepository;
import com.example.digiart.utils.ArtPiece;
import com.example.digiart.utils.FileUploadUtil;
import com.example.digiart.utils.Gcp;
import com.example.digiart.utils.ProductArt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArtRepository artRepository;
    @Autowired
    private ColorRepository colorRepository;

    @RequestMapping(value = "/product/addProduct/{id}", method = RequestMethod.POST)
    public String addProduct(@PathVariable Integer id, @RequestParam MultipartFile [] files, @RequestParam String[] size, @RequestParam String [] color,
                             @RequestParam String productDescription, @RequestParam String productHeading, @RequestParam String price,
                             @RequestParam String itemName) throws IOException {
//        System.out.println(size[0]);
//        System.out.println(size[1]);
        Product product = new Product();
        product.setDescription(productDescription);
        product.setProductheading(productHeading);
        product.setItemName(itemName);
        product.setPrice(price);

        List <Integer> a = new ArrayList<>();
        for(int i = 0; i < files.length; i++){
            Color c = new Color();
            c.setColor(color[i]);


//            String fileName = StringUtils.cleanPath(files[i].getOriginalFilename());
//            String uploadDir = "src/main/resources/static/products/" +  id.toString() + "/" + itemName;
//            c.setProductPicLocation("/products/" + id.toString() + "/" + itemName + "/" + fileName);
//            FileUploadUtil.saveFile(uploadDir, fileName, files[i]);
            String filepath = "products" + id.toString()  + itemName +color[i];
            Gcp gcp = new Gcp();
            gcp.uploading(files[i],filepath);
            c.setProduct(product);
            c.setProductPicLocation(filepath);
//            colors.add(c);
            colorRepository.save(c);
            System.out.println(c.getId());
            a.add(c.getId());
        }


        for(int i = 0; i < size.length; i++){
            Size s = new Size();
            s.setSize(size[i]);
            product.setSizes(s);
        }

        Art art = artRepository.findById(id).get();
        art.setProducts(product);
        artRepository.save(art);

        System.out.println(product.getProductId());


        Iterator<Integer> aIterator = a.iterator();
        while(aIterator.hasNext()) {
            Integer ele = aIterator.next();
            Color c = colorRepository.findById(ele).get();
            c.setpId(product.getProductId());
            colorRepository.save(c);
        }

//        System.out.println(product.getProductId());
//        System.out.println(art.getProducts().get());


        return "Product added!";

    }
}
