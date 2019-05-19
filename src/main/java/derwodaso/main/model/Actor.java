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
public class Actor {

    private final String name;
    private final Long id;
    private final String profilePath;
    private URL urlWiki;

    public Actor(String name, Long id, String profilePath) {
        this.name = name;
        this.id = id;
        this.profilePath = profilePath;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    public void setUrlWiki(URL urlWiki) {
        this.urlWiki = urlWiki;
    }

    public URL getUrlWiki() {
        return urlWiki;
    }

}
