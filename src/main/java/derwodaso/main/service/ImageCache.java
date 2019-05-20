/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author thors
 */
public class ImageCache {

    private static ImageCache cache;
    private static String cacheDir = "cache/";
    private int hitcount = 0;
    
    private ImageCache() {
        // nix
    }

    public static ImageCache getInstance() {
        if (cache == null) {
            cache = new ImageCache();
            new File(cacheDir).mkdir();
        }
        return cache;
    }

    public ImageIcon getImage(URL url) {

        String key = url.toExternalForm().replaceAll("[/:.]", "");

        try {
            if (HttpURLConnectionService.useCache && new File(cacheDir + key).exists()) {
                hitcount++;
//                System.out.println("hc = " + hitcount);
                BufferedImage im = ImageIO.read(new File(cacheDir + key));
                return new ImageIcon(im);
            }

            BufferedImage buffImage = ImageIO.read(url);
            ImageIcon image = new ImageIcon(buffImage);
            ImageIO.write(buffImage, "jpg", new File(cacheDir + key));
            return image;

        } catch (IOException ex) {
            Logger.getLogger(ImageCache.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public int getHitCount() {

        return hitcount;
    }

}
