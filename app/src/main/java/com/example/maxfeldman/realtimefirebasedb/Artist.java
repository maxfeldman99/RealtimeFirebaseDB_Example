package com.example.maxfeldman.realtimefirebasedb;

/**
 * Created by MAX FELDMAN on 12/05/2018.
 */

public class Artist {
    String artistId;
    String artistName;
    String artistGenre;

    public Artist(){

    }

    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}
