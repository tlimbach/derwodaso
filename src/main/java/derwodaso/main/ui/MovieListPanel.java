/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.ui;

import derwodaso.main.Controller;
import derwodaso.main.model.Caracter;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import derwodaso.main.model.Movie;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 *
 * @author tlimbach
 */
class MovieListPanel extends JPanel {

    private final JList<Movie> listMovies;
    private final JTextField txtSearch;
    private final DefaultListModel<Movie> movieModel;
    private List<Movie> movies;
    private final JPanel pnlInner;

    public MovieListPanel(final Controller controller) {
        setLayout(new BorderLayout());

        pnlInner = new JPanel();
        pnlInner.setLayout(new BorderLayout());
        pnlInner.setBorder(BorderFactory.createTitledBorder("andere Filme"));
        add(pnlInner, BorderLayout.CENTER);

        movieModel = new DefaultListModel<>();

        listMovies = new JList<>(movieModel);

        listMovies.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Movie movie = listMovies.getSelectedValue();
                    try {
                        controller.setMovie(movie);
                    } catch (Exception ex) {
                        Logger.getLogger(MovieListPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        listMovies.setCellRenderer(new ListCellRenderer<Movie>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Movie> list, Movie movie, int index, boolean isSelected, boolean cellHasFocus) {

                String res = movie.getTitle();
                if (movie.getCharacter() != null && movie.getCharacter().trim().length() != 0) {
                    res += " (" + movie.getCharacter() + ")";
                }
                final JLabel jLabel = new JLabel(res);

                if (isSelected) {
                    jLabel.setBackground(Color.DARK_GRAY);
                    jLabel.setForeground(Color.white);
                    jLabel.setOpaque(true);
                }

                return jLabel;

            }
        });
        txtSearch = new JTextField("Suchtext hier...");
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = txtSearch.getText();
                fillModel(searchText);
            }
        });

        pnlInner.add(new JScrollPane(listMovies), BorderLayout.CENTER);
        pnlInner.add(txtSearch, BorderLayout.SOUTH);
    }

    public void setMovies(Caracter character, List<Movie> movies) {
        this.movies = movies;
        pnlInner.setBorder(BorderFactory.createTitledBorder("andere Filme mit " + character.getActor().getName()));
        Collections.sort(movies);
        fillModel(null);
    }

    private void fillModel(String filter) {
        movieModel.clear();

        if (filter == null) {
            movies.forEach(m -> movieModel.addElement(m));
        } else {
            movies.stream().filter(m -> m.getTitle().toLowerCase().contains(filter.toLowerCase()) || (m.getCharacter() != null && m.getCharacter().toLowerCase().contains(filter.toLowerCase()))).forEach(m -> movieModel.addElement(m));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(280, 700);
    }

}
