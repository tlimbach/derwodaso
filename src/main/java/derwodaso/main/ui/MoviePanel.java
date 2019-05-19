/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import derwodaso.main.Controller;
import derwodaso.main.Helper;
import derwodaso.main.model.Actor;
import derwodaso.main.model.Caracter;
import derwodaso.main.model.Movie;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author tlimbach
 */
class MoviePanel extends JPanel {

    private final Controller controller;

    private JComboBox<Movie> cbxMovies;
    private JComboBox<Actor> cbxActors;
    private JComboBox<Caracter> cbxCharacters;
    private JLabel lblWikiMovieUrl;
    private JLabel lblWikiActorUrl;
    private JLabel lblMovieUrl;
    private JPanel pnlPoster;
    private JLabel lblPoster;
    private JPanel pnlLeft;
    private URL wikiMovieUrl;
    private URL wikiActorUrl;
    private URL movieUrl;

    public MoviePanel(Controller controller) {
        init();

        this.controller = controller;
    }

    private void init() {
        setLayout(new BorderLayout());
        pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));

        cbxMovies = new JComboBox<>();
        cbxMovies.setEditable(true);

        cbxMovies.addActionListener(a -> {

            if (cbxMovies.getSelectedItem() != null && cbxMovies.getSelectedItem() instanceof Movie) {
                Movie movie = (Movie) cbxMovies.getSelectedItem();
                try {
                    controller.setMovie(movie);
                } catch (Exception ex) {
                    Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        cbxMovies.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                    searchMovie((String) cbxMovies.getSelectedItem());
                }
            }
        });
        cbxMovies.setRenderer(new ListCellRenderer<Movie>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Movie> list, Movie value, int index, boolean isSelected, boolean cellHasFocus) {
                final JLabel lbl = new JLabel(value.getTitle());

                if (isSelected) {
                    lbl.setBackground(Color.DARK_GRAY);
                    lbl.setForeground(Color.white);
                    lbl.setOpaque(true);
                }

                return lbl;
            }
        });

        cbxActors = new JComboBox<>();
        cbxActors.setEditable(true);
        cbxActors.addActionListener((a)
                -> {
            try {
                if (cbxActors.getSelectedItem() != null && cbxActors.getSelectedItem() instanceof Actor) {
                    controller.setActor((Actor) cbxActors.getSelectedItem());
                }
            } catch (Exception ex) {
                Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cbxActors.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                    try {
                        controller.searchActor((String) cbxActors.getSelectedItem());
                    } catch (Exception ex) {
                        Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        cbxCharacters = new JComboBox<>();
        cbxCharacters.addActionListener((a)
                -> {
            try {
                if (cbxCharacters.getSelectedItem() != null) {
                    controller.setCharacter((Caracter) cbxCharacters.getSelectedItem());
                }
            } catch (Exception ex) {
                Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        pnlPoster = new JPanel();
        pnlPoster.setLayout(new BorderLayout());
        lblPoster = new JLabel();
        pnlPoster.add(lblPoster, BorderLayout.CENTER);

        JPanel pnlSearchMovie = new JPanel();
        pnlSearchMovie.setBorder(BorderFactory.createTitledBorder("Film"));
        pnlSearchMovie.setLayout(new BorderLayout());
        pnlSearchMovie.add(cbxMovies, BorderLayout.CENTER);
        pnlSearchMovie.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        pnlLeft.add(pnlSearchMovie);
//        add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel pnlActor = new JPanel();
        pnlActor.setLayout(new BorderLayout());
        pnlActor.setBorder(BorderFactory.createTitledBorder("Schauspieler"));
        pnlActor.add(cbxActors, BorderLayout.CENTER);
        pnlActor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        pnlLeft.add(pnlActor);
//        add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel pnlCharacter = new JPanel();
        pnlCharacter.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        pnlCharacter.setLayout(new BorderLayout());
        pnlCharacter.setBorder(BorderFactory.createTitledBorder("Rolle"));
        pnlCharacter.add(cbxCharacters, BorderLayout.CENTER);;
        pnlLeft.add(pnlCharacter);
        pnlLeft.add(Box.createRigidArea(new Dimension(0, 5)));

        final JPanel noIdea = new JPanel();
        noIdea.setLayout(new BorderLayout());
        lblWikiMovieUrl = new JLabel("lblWikiMovieUrl");
        lblWikiMovieUrl.setForeground(Color.BLUE.darker());
        lblWikiMovieUrl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblWikiMovieUrl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (wikiMovieUrl != null) {
                    try {
                        Desktop.getDesktop().browse(wikiMovieUrl.toURI());
                    } catch (URISyntaxException | IOException ex) {
                        Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        lblWikiActorUrl = new JLabel("lblWikiActorUrl");
        lblWikiActorUrl.setForeground(Color.BLUE.darker());
        lblWikiActorUrl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblWikiActorUrl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (wikiActorUrl != null) {
                    try {
                        Desktop.getDesktop().browse(wikiActorUrl.toURI());
                    } catch (URISyntaxException | IOException ex) {
                        Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
        lblMovieUrl = new JLabel("lblMovieUrl");
        lblMovieUrl.setForeground(Color.BLUE.darker());
        lblMovieUrl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblMovieUrl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (movieUrl != null) {
                    try {
                        Desktop.getDesktop().browse(movieUrl.toURI());
                    } catch (URISyntaxException | IOException ex) {
                        Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

        JPanel pnlWikiLinks = new JPanel();
        noIdea.add(pnlWikiLinks, BorderLayout.CENTER);
        pnlWikiLinks.setLayout(new BoxLayout(pnlWikiLinks, BoxLayout.Y_AXIS));
        pnlWikiLinks.add(lblWikiMovieUrl);
        pnlWikiLinks.add(lblMovieUrl);
        pnlWikiLinks.add(lblWikiActorUrl);
        pnlLeft.add(noIdea); // nmagic Panel!

        add(pnlLeft, BorderLayout.CENTER);
        add(pnlPoster, BorderLayout.EAST);
    }

    private void searchMovie(String name) {
        try {
            controller.searchMovies(name);
        } catch (Exception ex) {
            Logger.getLogger(MoviePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setActors(List<Actor> actors) {
        cbxActors.removeAllItems();
        actors.forEach(a -> cbxActors.addItem(a));
    }

    public void setCharacters(List<Caracter> characters) {
        cbxCharacters.removeAllItems();
        characters.forEach(cbxCharacters::addItem);
    }

    void setMovies(List<Movie> movies) {
        cbxMovies.removeAllItems();
        movies.forEach(cbxMovies::addItem);
    }

    void selectChacter(Caracter c) {
        cbxActors.setSelectedItem(c.getActor());

        if (c.getName().equals("---")) {
            cbxCharacters.removeAllItems();
            cbxMovies.removeAllItems();
        } else {
            cbxCharacters.setSelectedItem(c);
        }
    }

    void selectMovieTitel(Movie movie) {

        boolean found = false;

        for (int t = 0; t < cbxMovies.getItemCount(); t++) {
            if (cbxMovies.getItemAt(t).equals(movie)) {
                cbxMovies.setSelectedIndex(t);
                found = true;
            }
        }

        if (!found) {
            cbxMovies.addItem(movie);
            cbxMovies.setSelectedItem(movie);
        }

    }

    void setPoster(URL url) {
        if (url != null) {
            ImageIcon image = new ImageIcon(url);
            image = new ImageIcon(
                    Helper.getScaledImage(image.getImage(), (370 * image.getIconWidth()) / image.getIconHeight(), 370));
            lblPoster.setIcon(image);
        } else {
            lblPoster.setIcon(null);
        }

    }

    void setMovieWikiUrl(URL wikiMovie) {
        this.wikiMovieUrl = wikiMovie;
        lblWikiMovieUrl.setText(this.wikiMovieUrl == null ? null : this.wikiMovieUrl.toExternalForm());
    }

    void setMovieUrl(URL url) {
        this.movieUrl = url;
        lblMovieUrl.setText(this.movieUrl == null ? null : this.movieUrl.toExternalForm());
    }

    void setActorWiki(URL url) {
        this.wikiActorUrl = url;
        lblWikiActorUrl.setText(this.wikiActorUrl == null ? null : this.wikiActorUrl.toExternalForm());

    }

}
