/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.model;

import java.net.URL;
import java.util.List;

/**
 *
 * @author tlimbach
 */
public class Movie {

    private String backdropPath;
    private String releaseDate;
    String title;
    String posterPath;
    Long id;
    private String character;
    
    private URL urlWiki;
    private URL urlMovie;

    public Movie(String title, String posterPath, Long id) {
        this.title = title;
        this.posterPath = posterPath;
        this.id = id;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setReleaseDate(String relDate) {
        this.releaseDate = relDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Long getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setUrlWiki(URL urlWiki) {
        this.urlWiki = urlWiki;
    }

    public URL getUrlWiki() {
        return urlWiki;
    }
    
    public void setUrlMovie(URL url) {
        this.urlMovie = url;
    }

    public URL getUrlMovie() {
        return urlMovie;
    }
    
    

}
