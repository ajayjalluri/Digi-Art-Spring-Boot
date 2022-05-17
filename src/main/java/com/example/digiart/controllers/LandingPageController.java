package com.example.digiart.controllers;

import com.example.digiart.entities.Art;
import com.example.digiart.entities.Color;
import com.example.digiart.entities.Orders;
import com.example.digiart.entities.User;
import com.example.digiart.exceptions.IdNotFoundException;
import com.example.digiart.repositories.*;
import com.example.digiart.utils.ArtPiece;
import com.example.digiart.utils.ArtistInfo;
import com.example.digiart.utils.Gcp;
import com.example.digiart.utils.LandingPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000/","https://digi-art-fe-urtjok3rza-wl.a.run.app/"})
public class LandingPageController {
    @Autowired
    private ArtRepository artRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/page/landingPage", method = RequestMethod.GET)
    public LandingPage  getLandingPage() {

        LandingPage landingPage = new LandingPage();

        List<ArtPiece> lastTenArtPiece = new ArrayList<>();
        Collection<Art> lastTen = artRepository.lastTen();
        Gcp gcp = new Gcp();
        Iterator<Art> art = lastTen.iterator();
        while(art.hasNext()) {


            ArtPiece artPiece = new ArtPiece();
            Art artObject = art.next();
//            artPiece.art = artObject;
            artPiece.artId = artObject.getArtId();
            artPiece.artName = artObject.getArtName();
            artPiece.artLikes = artObject.getLikes();
            if (artObject.getArtLocation()!=null){
                artPiece.artPicLocation = gcp.downloading(artObject.getArtLocation());
            }

            User user = userRepository.findById(artObject.getUserID()).get();
            artPiece.artistName = user.getUserName();
            artPiece.artisId = user.getUserId();
            if(user.getProfilePicLocation()!=null)
            {
                artPiece.artistProfilePic = gcp.downloading(user.getProfilePicLocation());

            }

            lastTenArtPiece.add(artPiece);
        }
        landingPage.landingPageNew = lastTenArtPiece;

        Collection<User> topArtist = userRepository.topArtist();
        List<ArtistInfo> topArtistInfo = new ArrayList<>();

        Iterator<User> artistInfo = topArtist.iterator();
        while(artistInfo.hasNext()) {
            ArtistInfo artist = new ArtistInfo();
            User artistObject = artistInfo.next();
            artist.userId = artistObject.getUserId();
            artist.userBio = artistObject.getBio();
            artist.userName = artistObject.getUserName();

            if (artistObject.getProfilePicLocation()!=null){
                artist.userprofilePicLocation = gcp.downloading(artistObject.getProfilePicLocation());

            }
            artist.userFollowers = artistObject.getNoFollowers();
            topArtistInfo.add(artist);
        }
        landingPage.topArtist = topArtistInfo;

        List<ArtPiece> topOrderArtPiece = new ArrayList<>();
        List<Integer> dummyForOrders = orderRepository.topOrders();

        System.out.println(dummyForOrders);
        Iterator<Integer> dI = dummyForOrders.iterator();

        Set<Integer> pI = new HashSet<Integer>();
        while ( dI.hasNext() ){
            Integer a = colorRepository.productId(dI.next());
            pI.add(productRepository.artId(a));
        }
        System.out.println(pI);
        Iterator<Integer> pIterator = pI.iterator();

        while(pIterator.hasNext()) {

            ArtPiece artPiece = new ArtPiece();
            Art artObject = artRepository.findById(pIterator.next()).get();
            artPiece.artId = artObject.getArtId();
            artPiece.artName = artObject.getArtName();
            artPiece.artLikes = artObject.getLikes();
            if (artObject.getArtLocation()!=null){
                artPiece.artPicLocation = gcp.downloading(artObject.getArtLocation());
            }

            User user = userRepository.findById(artObject.getUserID()).get();
            artPiece.artistName = user.getUserName();
            artPiece.artisId = user.getUserId();
            if(user.getProfilePicLocation()!=null)
            {
                artPiece.artistProfilePic = gcp.downloading(user.getProfilePicLocation());

            }
            topOrderArtPiece.add(artPiece);
        }

        landingPage.topOrders = topOrderArtPiece;

        return landingPage;

    }

}
