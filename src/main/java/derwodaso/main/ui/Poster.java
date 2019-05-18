/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.ui;

import derwodaso.main.Helper;
import java.awt.BorderLayout;
import java.awt.Component;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author thors
 */
public class Poster {

    //TODO: Poster nur einmal anzeigen, ggf. Bild tauschen
    public Poster(Component parent, URL url) {
        JDialog dialog = new JDialog();
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        JLabel lbl = new JLabel();
        ImageIcon image = new ImageIcon(url);
        image = new ImageIcon(
                Helper.getScaledImage(image.getImage(), (500 * image.getIconWidth()) / image.getIconHeight(), 500));
        lbl.setIcon(image);
        dialog.add(lbl, BorderLayout.CENTER);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);

    }

}
