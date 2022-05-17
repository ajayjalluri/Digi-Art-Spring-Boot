package com.example.digiart.controllers;

import com.example.digiart.entities.*;
import com.example.digiart.exceptions.IdNotFoundException;
import com.example.digiart.models.CheckoutPage;
import com.example.digiart.repositories.*;
import com.example.digiart.utils.Gcp;
import com.example.digiart.utils.JwtUtil;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class OrderController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ArtRepository artRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    CartProductsRepository cartProductsRepository;

    @Autowired
    private Gcp gcp;

    @RequestMapping(value = "/cart/checkout/",method= RequestMethod.POST)
    public String orderCheckout(@RequestHeader("Authorization") String token ,@RequestBody User user1 ){
        Integer user_id = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if(userRepository.existsById(user_id)){


            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("User: " +user_id+" does Not Exist.!");


        User user = userRepository.findById(user_id).get();
        List<CartProductsInfo> products = user.getCarts().getProducts();
        for(CartProductsInfo product: products){
            Orders ord = new Orders();
            ord.setProduct_Id(product.getProductId());

            Color c = colorRepository.findById(product.getProductId()).get();
            Integer artId = productRepository.artId(c.getpId());
            Art art = artRepository.findById(artId).get();
            art.setQtypurchased(product.getQuantity());
            artRepository.save(art);



            ord.setSize(product.getSize());
            ord.setQuantity(product.getQuantity());
            ord.setPrice(product.getPrice());
            user.setOrder(ord);
            cartProductsRepository.delete(product);
        }
        Cart cart  = user.getCarts();
        Integer cart_id = cart.getCartId();
        Cart tempcart = cartRepository.findById(cart_id).get();
        tempcart.updateProducts(new ArrayList<>());
        cartRepository.save(tempcart);
        user.setCarts(tempcart);


        user.setAddress(user1.getAddress());
        user.setEmail(user1.getEmail());
        user.setPhonenumber(user1.getPhonenumber());
        userRepository.save(user);

        return "Successfully checked-out";
    }

    @RequestMapping(value = "/cart/checkoutpage/",method = RequestMethod.GET)
    public CheckoutPage getProductsfromCart(@RequestHeader("Authorization") String token) {
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if (userRepository.existsById(userid)) {
            System.out.println("Id is there");
        }
        User user = userRepository.findById(userid).get();
        CheckoutPage checkoutPage = new CheckoutPage();
        Cart c  = userRepository.findById(userid).get().getCarts();
        List<CartProductsInfo> cl= c.getProducts();
        Iterator<CartProductsInfo> ci = cl.iterator();

        while(ci.hasNext()){
            CartProductsInfo cio = ci.next();
            String location = colorRepository.findById(cio.getProductId()).get().getProductPicLocation();
            Integer i = cio.getProductId();
            Integer p = colorRepository.productId(i);
            Product product = productRepository.findById(p).get();
            cio.Heading = product.getProductheading();
            cio.artName= product.getItemName();
            Integer a = productRepository.artId(p);
            Integer u = artRepository.findById(a).get().getUserID();

            cio.artistName = userRepository.findById(u).get().getUserName();
            if (location !=null){
                cio.image = gcp.downloading(location);
            }


        }
        checkoutPage.cartProductsInfoList = cl;
        checkoutPage.phonenumber = user.getPhonenumber();
        checkoutPage.address =user.getAddress();
        checkoutPage.emailid =user.getEmail();




        return checkoutPage;


    }
}

