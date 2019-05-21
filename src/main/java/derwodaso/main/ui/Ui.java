/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import derwodaso.main.Controller;
import derwodaso.main.model.Actor;
import derwodaso.main.model.Caracter;
import derwodaso.main.model.Movie;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author tlimbach
 */
public class Ui {

    private final JFrame frame;
    private final Controller controller;
    private final Border border = BorderFactory.createEtchedBorder();

    private final MoviePanel pnlMovie;
    private final InfoPanel pnlInfo;
    private final ActorThumbnailsPanel pnlActorThumbnails;
    private final MovieListPanel pnlMovieList;

    public Ui(Controller controller) throws MalformedURLException, UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        this.controller = controller;

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(1200, 700));
        frame.setResizable(false);

        BorderLayout manager = new BorderLayout();
        manager.setHgap(20);
        manager.setVgap(15);
        frame.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        frame.setLayout(manager);

        pnlMovie = createPanelMovie();
        pnlInfo = createPanelInfo();
        pnlMovieList = createPanelMovieList();

        pnlMovieList.setBorder(border);
        pnlInfo.setBorder(border);
        pnlMovie.setBorder(border);

        pnlActorThumbnails = new ActorThumbnailsPanel(controller);

        frame.add(pnlInfo, BorderLayout.WEST);

        frame.add(pnlMovie, BorderLayout.CENTER);
        frame.add(pnlMovieList, BorderLayout.EAST);
        
        frame.add(pnlActorThumbnails, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.pack();
        frame.setTitle("Derwodaso");
        frame.setVisible(true);
        
        setPoster(null);
        setActorImage("", new URL("http://127.0.0.1/ismiegal"));
        setCharctersForMovie(Collections.emptyList());

    }
    
    public Container getContainer() {
        return frame; 
    }

    private InfoPanel createPanelInfo() {
        return new InfoPanel();
    }

    private MoviePanel createPanelMovie() {
        return new MoviePanel(controller);
    }

    private MovieListPanel createPanelMovieList() {
        return new MovieListPanel(controller);
    }

    public void setMoviesForSelection(List<Movie> movies) {
        pnlMovie.setMovies(movies);
    }

    public void setActorsForSelection(List<Actor> actors) throws Exception {
        pnlMovie.setActors(actors);
    }

    public void setSelectedMovie(Movie movie, URL urlPoster, List<Actor> actors, List<Caracter> characters) throws Exception {

        pnlMovie.setActors(actors);
        pnlMovie.setCharacters(characters);
        pnlMovie.selectMovieTitel(movie);
        pnlInfo.setMoviePoster(urlPoster);
        
    }
    
    public void setUrlMovieWiki(URL url) {
        pnlMovie.setMovieWikiUrl(url);
    }
    
     public void setUrlMovie(URL url) {
        pnlMovie.setMovieUrl(url);
    }
    
    public void setActorWiki(URL url) {
        pnlMovie.setActorWiki(url);
    }

    public void setCaracter(Caracter c) {
        pnlMovie.selectChacter(c);
    }

    public void setActorImage(String actorName, URL url) {
        pnlMovie.setActorPoster(actorName, url);
    }

    public void setPoster(URL url) {
        pnlInfo.setMoviePoster(url);
    }
    
    
    public synchronized  void setCharctersForMovie(List<Caracter> characters) throws MalformedURLException {
//        pnlActorThumbnails.setCharacters(null);
//        frame.revalidate();
        pnlActorThumbnails.setCharacters(characters);
        frame.revalidate();
    }

    public void setMoviesForCharacter(Caracter character, List<Movie> movies) {
        pnlMovieList.setMovies(character, movies);
    }


    public void showNoResults(String text) {
        JOptionPane.showMessageDialog(frame, text, "Leider nichts gefunden", JOptionPane.INFORMATION_MESSAGE);
    }
   

    public static Component createRidgitArea(int height) {
            return Box.createRigidArea(new Dimension(5,height));
        }

}
