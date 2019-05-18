package derwodaso.main.ui;

import derwodaso.main.Controller;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import derwodaso.main.Helper;
import derwodaso.main.model.Actor;
import derwodaso.main.model.Caracter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

public class ActorThumbnailsPanel extends JPanel {

    private final Controller controller;

    /**
     *
     * @param controller
     */
    public ActorThumbnailsPanel(Controller controller) {
        init();
        this.controller = controller;
    }

    private void init() {
        setLayout(new FlowLayout());
    }

    public void setCharacters(List<Caracter> characters) throws MalformedURLException {
        removeAll();
        for (int t = 0; t < characters.size(); t++) {

            if (characters.get(t).getActor().getProfilePath() != null) {

                CharacterImageLabel cLabel = new CharacterImageLabel(characters.get(t));
                cLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        CharacterImageLabel lbl = (CharacterImageLabel) e.getSource();
                        try {
                            controller.setActor(lbl.getCaracter().getActor());
                        } catch (Exception ex) {
                            Logger.getLogger(ActorThumbnailsPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                SwingUtilities.invokeLater(() -> {
                    add(cLabel);
                });
            }
        }
    }

    private static class CharacterImageLabel extends JPanel {

        private final Caracter caracter;

        public CharacterImageLabel(Caracter caracter) throws MalformedURLException {
            setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
            this.caracter = caracter;
            String imgPath = caracter.getActor().getProfilePath();
            Actor actor = caracter.getActor();
            final BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

            setLayout(boxLayout);
            final JLabel lblCharacterName = new JLabel(caracter.getName(), JLabel.CENTER);
            lblCharacterName.setFont(new Font("Arial", Font.BOLD, 12));
            lblCharacterName.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            ImageIcon image = new ImageIcon(createUrl(imgPath));
            image = new ImageIcon(
                    Helper.getScaledImage(image.getImage(), (155 * image.getIconWidth()) / image.getIconHeight(), 155));
            final JLabel lblImg = new JLabel(image);
            lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            final JLabel lblActorName = new JLabel(actor.getName(), JLabel.CENTER);
            lblActorName.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            
            
            
            add(createRidgitArea());
            add(lblCharacterName);
            add(createRidgitArea());
            add(lblImg);
            add(createRidgitArea());
            add(lblActorName);
            add(createRidgitArea());
        }
        
        private Component createRidgitArea() {
            return Box.createRigidArea(new Dimension(5,3));
        }

        private URL createUrl(String imgPath) throws MalformedURLException {
            String basePath = "http://image.tmdb.org/t/p/w185//";
            return new URL(basePath + imgPath);

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 200);
        }

        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        public Caracter getCaracter() {
            return caracter;
        }

    }

}
