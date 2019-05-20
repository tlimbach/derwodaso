/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main;

import derwodaso.main.model.Actor;
import derwodaso.main.model.Caracter;
import derwodaso.main.model.Movie;
import derwodaso.main.service.FindService;
import java.net.MalformedURLException;

import derwodaso.main.ui.Ui;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author tlimbach
 */
public class Controller {

    private FindService finder;
    private Ui ui;
    private List<Movie> movies;
    private List<Actor> actors;
    private List<Caracter> characters;
    private Movie currentMovie;

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public Controller(String apiKey) throws MalformedURLException {
        try {

            this.movies = new ArrayList<>();
            ui = new Ui(this);
            finder = new FindService(ui.getContainer());
            finder.setApiKey(apiKey);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) throws MalformedURLException {

        if (args.length != 1) {
            System.err.println("Please provide api key!");
        } else {
            new Controller(args[0]);
        }
    }

    public void searchMovies(String name) throws Exception {
        movies = finder.searchMovies(name);

        if (movies.isEmpty()) {
            showNoResults("Es wurden keine passenden Filme gefunden.");
        }
        ui.setMoviesForSelection(movies);
    }

    private void showNoResults(String text) {
        ui.showNoResults(text);
    }

    public void searchActor(String name) throws Exception {
        actors = finder.searchActors(name);

        if (actors.isEmpty()) {
            showNoResults("Es wurden keine Schauspieler gefunden.");
        }
        ui.setActorsForSelection(actors);
        ui.setPoster(null);

    }

    public void setMovie(Movie movie) throws Exception {

        if (this.currentMovie != movie) {
            this.currentMovie = movie;
            setMovieToUi();
        }
    }

    private void setMovieToUi() throws Exception {
        if (this.currentMovie != null) {
            characters = finder.searchCharacters(currentMovie);
            final List<Actor> actors = characters.stream().map(c -> c.getActor()).collect(Collectors.toList());

            String url = "http://image.tmdb.org/t/p/w300//" + this.currentMovie.getPosterPath();

            Movie _movie = this.currentMovie;
            setUrlWiki(_movie);
            setUrlMovie(_movie);

            ui.setSelectedMovie(this.currentMovie, new URL(url), actors, characters);

            new Thread(() -> {
                try {
                    ui.setCharctersForMovie(characters);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();

        }
    }

    private void setUrlWiki(Movie _movie) {
        new Thread(() -> {
            if (_movie.getUrlWiki() == null) {
                URL urlWiki;
                try {
                    urlWiki = finder.searchMovieWikiUrl(_movie);
                    _movie.setUrlWiki(urlWiki);
                } catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ui.setUrlMovieWiki(_movie.getUrlWiki());
            
        }).start();
    }

    private void setUrlMovie(Movie _movie) {
        new Thread(() -> {
            if (_movie.getUrlMovie() == null) {
                URL urlMovie;
                try {
                    urlMovie = finder.searchMovieUrl(_movie);
                    _movie.setUrlMovie(urlMovie);
                } catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ui.setUrlMovie(_movie.getUrlMovie());
            
        }).start();
    }

    public void setActor(Actor actor) throws MalformedURLException, Exception {
        setWikiUrlActor(actor);
        if (characters != null) {
            for (Caracter c : characters) {
                if (c.getActor().equals(actor)) {
                    setCharacter(c);
                    return;
                }
            }
        }

        setCharacter(new Caracter("---", actor));
        ui.setCharctersForMovie(Collections.emptyList());
        ui.setUrlMovieWiki(null);

    }

    private void setWikiUrlActor(Actor actor) {
        Actor _actor = actor;
        new Thread(() -> {
            if (_actor.getUrlWiki() == null) {
                URL urlWiki;
                try {
                    urlWiki = finder.searchActorWikiUrl(_actor);
                    _actor.setUrlWiki(urlWiki);
                } catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            ui.setActorWiki(_actor.getUrlWiki());
            
        }).start();
    }

    public void setCharacter(Caracter character) {
        ui.setCaracter(character);

        String url500 = "http://image.tmdb.org/t/p/w500//" + character.getActor().getProfilePath();
        try {
            ui.setActorImage(new URL(url500));
            ui.setMoviesForCharacter(character, finder.searchMoviesByActor(character));
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
