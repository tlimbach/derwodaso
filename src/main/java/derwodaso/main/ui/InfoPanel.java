/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.ui;

import java.awt.BorderLayout;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import derwodaso.main.Helper;
import java.awt.Dimension;

/**
 *
 * @author tlimbach
 */
class InfoPanel extends JPanel {
    
    JLabel lblImage;
    
    public InfoPanel() {
        init();
    }
    
    private void init() {
        lblImage = new JLabel();
        
        lblImage.setPreferredSize(new Dimension(300, 300));
        lblImage.setHorizontalAlignment(JLabel.CENTER);
        
        setLayout(new BorderLayout());
        add(lblImage, BorderLayout.CENTER);
    }
    
    public void setMoviePoster(URL url) {
        
        if (url != null) {
            ImageIcon image = new ImageIcon(url);
            image = new ImageIcon(
                    Helper.getScaledImage(image.getImage(), (370 * image.getIconWidth()) / image.getIconHeight(), 370));
            lblImage.setIcon(image);
        } else {
            lblImage.setIcon(null);
        }
        
    }
}
