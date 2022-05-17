package com.example.digiart.utils;

import com.example.digiart.entities.Art;
import com.example.digiart.entities.Color;
import com.example.digiart.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ArtAndUser {
    private Art art;



    private String artistName;
    private String artistBio;
    private String artistProfilePic;
    private List<List<Colorproductpage>> colors = new ArrayList<>();

    public List<List<Colorproductpage>> getColors() {
        return colors;
    }

    public void setColors(List<Colorproductpage> colors) {
        this.colors.add(colors);
    }

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistBio() {
        return artistBio;
    }

    public void setArtistBio(String artistBio) {
        this.artistBio = artistBio;
    }

    public String getArtistProfilePic() {
        return artistProfilePic;
    }

    public void setArtistProfilePic(String artistProfilePic) {
        this.artistProfilePic = artistProfilePic;
    }




}
