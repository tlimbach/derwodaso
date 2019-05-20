/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author thors
 */
public class ImageCacheTest {

    public ImageCacheTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    // Test funktioniert natürlich nicht, wenn schon Bilder im Cache sind
    @Test
    @Ignore
    public void testGetImage() throws MalformedURLException {
        final ImageCache cache = ImageCache.getInstance();

        String url = "http://image.tmdb.org/t/p/w185///76ZJ9HN0YPKnuu7HGbtkNc3TABl.jpg";
        ImageIcon image = cache.getImage(new URL(url));

        assertTrue(image != null);
        assertThat(cache.getHitCount(), is(0));
        
        image = cache.getImage(new URL(url));

        assertTrue(image != null);
        assertThat(cache.getHitCount(), is(1));

    }

}
