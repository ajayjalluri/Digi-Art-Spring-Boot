package com.example.digiart.controllers;

import com.example.digiart.entities.*;
import com.example.digiart.exceptions.IdNotFoundException;
import com.example.digiart.models.ListofCartProductsinfo;
import com.example.digiart.repositories.*;
import com.example.digiart.utils.Gcp;
import com.example.digiart.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class CartController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    CartProductsRepository cartProductsRepository;

    @Autowired
    ArtRepository artRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Gcp gcp;

    @RequestMapping(value = "/cart/incrementqty/{cartproductid}",method = RequestMethod.PUT)
    public String addProductToCart(@RequestHeader("Authorization") String token,
                                @PathVariable("cartproductid")Integer cartproductid
    ){
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if(userRepository.existsById(userid)){


            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("User: " +userid+" does Not Exist.!");

        if(cartProductsRepository.existsById(cartproductid)){


            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("Product: " +cartproductid+" does Not Exist.!");


        User user = userRepository.findById(userid).get();
        Cart cart  = user.getCarts();
        Integer cart_id = cart.getCartId();
        CartProductsInfo c = cartProductsRepository.findById(cartproductid).get();
        c.setQuantitytoq("1");
        cartProductsRepository.save(c);
        return "Qunatity incremented ";
    }

    @RequestMapping(value = "/cart/decrementqty/{cartproductid}",method = RequestMethod.PUT)
    public String decrementProductfromCart(@RequestHeader("Authorization") String token,
                                   @PathVariable("cartproductid")Integer cartproductid
    ){
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if(userRepository.existsById(userid)){


            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("User: " +userid+" does Not Exist.!");

        if(cartProductsRepository.existsById(cartproductid)){


            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("Product: " +cartproductid+" does Not Exist.!");


        User user = userRepository.findById(userid).get();
        Cart cart  = user.getCarts();
        Integer cart_id = cart.getCartId();
        CartProductsInfo c = cartProductsRepository.findById(cartproductid).get();

        if(c.getQuantity().equals("1"))
        {
            cartProductsRepository.delete(c);
            return "Quantity Deleted";

        }
        else{
            c.decrementQuantity();
            cartProductsRepository.save(c);
        }

        return "Quantity Decremented";
    }

    @RequestMapping(value = "/cart/delete/{cartproductid}",method = RequestMethod.DELETE)
    public String deleteProductfromCart(@RequestHeader("Authorization") String token,
                                        @PathVariable("cartproductid")Integer cartproductid
    ){
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if(userRepository.existsById(userid)){
            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("User: " +userid+" does Not Exist.!");
        if(cartProductsRepository.existsById(cartproductid)){
            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("Product: " +cartproductid+" does Not Exist.!");
        User user = userRepository.findById(userid).get();
        Cart cart  = user.getCarts();
        Integer cart_id = cart.getCartId();
        CartProductsInfo c = cartProductsRepository.findById(cartproductid).get();
        cartProductsRepository.delete(c);
        cartRepository.save(cart);
        userRepository.save(user);
            return "Product Deleted";
    }

    @RequestMapping(value = "/cart/clear/",method = RequestMethod.DELETE)
    public String clearCart(@RequestHeader("Authorization") String token)
    {
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if(userRepository.existsById(userid)){
            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("User: " +userid+" does Not Exist.!");
//        if(cartProductsRepository.existsById(cartproductid)){
//            System.out.println("Id is there");
//        }
        User user =  userRepository.findById(userid).get();
        Cart carts = user.getCarts();
        List<CartProductsInfo> cf = carts.getProducts();
        Iterator<CartProductsInfo> cfi = cf.iterator();
        while(cfi.hasNext()){
            CartProductsInfo a = cfi.next();
            cartProductsRepository.delete(a);
        }
        carts.updateProducts(new ArrayList<CartProductsInfo>());
        cartRepository.save(carts);
        userRepository.save(user);


        return "Products Removed";
    }


    @RequestMapping(value = "/cart/addproducts/",method = RequestMethod.POST)
    public String addProductsToCart(@RequestHeader("Authorization") String token,@RequestBody ListofCartProductsinfo listcartproducts){
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if(userRepository.existsById(userid)){
            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("User: " +userid+" does Not Exist.!");
        List<CartProductsInfo> cartproducts = listcartproducts.cartProductsInfoList;
        Iterator<CartProductsInfo>cartProductsInfoIterator = cartproducts.iterator();
        User user = userRepository.findById(userid).get();
        Cart cart  = user.getCarts();
        Integer cart_id = cart.getCartId();
        while (cartProductsInfoIterator.hasNext()){
            CartProductsInfo c = cartProductsInfoIterator.next();
            Optional<CartProductsInfo> c1 = cartProductsRepository.cartproductinfoid(cart_id,c.getProductId(),c.getSize());
            System.out.println(c1.isEmpty());
            if (c1.isEmpty()){

                cart.setProducts(c);
                cartProductsRepository.save(c);


            }
            else{

                CartProductsInfo cf = c1.get();
                cf.setQuantitytoq(c.getQuantity());
                cartProductsRepository.save(cf);
            }
        }
        cartRepository.save(cart);



        return "Products added to cart";
    }

    @RequestMapping(value = "/cart/getproducts/",method = RequestMethod.GET)
    public List<CartProductsInfo> getProductsfromCart(@RequestHeader("Authorization") String token) {
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if (userRepository.existsById(userid)) {


            System.out.println("Id is there");
        }
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


        return cl;


    }

    @RequestMapping(value = "/cart/deleteproduct/{product_id}",method = RequestMethod.DELETE)
    public String deleteProductFromCart(@RequestHeader("Authorization") String token,
                                   @PathVariable("product_id")Integer product_id
    ){
        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
        if(userRepository.existsById(userid)){


            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("User: " +userid+" does Not Exist.!");

        if(colorRepository.existsById(product_id)){


            System.out.println("Id is there");
        }
        else throw new IdNotFoundException("Product: " +product_id+" does Not Exist.!");


        User user = userRepository.findById(userid).get();
        Cart cart  = user.getCarts();
        Integer cart_id = cart.getCartId();
        List<CartProductsInfo> products = cartRepository.findById(cart_id).get().getProducts();
        List<CartProductsInfo> temp = new ArrayList<>();
        int i = 0;
        for(CartProductsInfo product : products){
            if(product.getProductId()!=product_id) {
                temp.add(product);
            }
            else {
                i = 1;
            }
        }
        if (i == 0) {
            throw new IdNotFoundException("Product: " +product_id+" does Not Exist in cart.");
        }
        cart.updateProducts(temp);
        cartRepository.save(cart);
        user.setCarts(cart);
        return "Product successfully deleted";

    }


//    @RequestMapping(value = "/cart/addproduct/{product_id}/{size}",method = RequestMethod.POST)
//    public String addProductToCart(@RequestHeader("Authorization") String token,
//                                   @PathVariable("product_id")Integer product_id, @PathVariable("size")String size
//    ){
//        Integer userid = userRepository.findByUserName(jwtUtil.extractUsername(token.substring(7))).get().getUserId();
//        if(userRepository.existsById(userid)){
//
//
//            System.out.println("Id is there");
//        }
//        else throw new IdNotFoundException("User: " +userid+" does Not Exist.!");
//
//        if(colorRepository.existsById(product_id)){
//
//
//            System.out.println("Id is there");
//        }
//        else throw new IdNotFoundException("Product: " +product_id+" does Not Exist.!");
//
//
//        User user = userRepository.findById(userid).get();
//        Cart cart  = user.getCarts();
//        Integer cart_id = cart.getCartId();
//        CartProductsInfo cartpi = new CartProductsInfo();
//        cartpi.setProductId(product_id);
//        cartpi.setSize(size);
//        cart.setProducts(cartpi);
//        cartRepository.save(cart);
//        return "Product added to cart";
//    }

}
