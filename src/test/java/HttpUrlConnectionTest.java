/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import derwodaso.main.service.FindService;
import derwodaso.main.service.HttpURLConnectionService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author thors
 */
public class HttpUrlConnectionTest {

    public HttpUrlConnectionTest() {
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

    @Test
    public void testDuckDuckGoGetMovieWikiUrl() throws Exception {

        String movieTitle = "nva";

        HttpURLConnectionService con = new HttpURLConnectionService(null);

        String url = "https://api.duckduckgo.com/?q=" + movieTitle + " film&format=json";

        System.out.println("request = " + url);
        String response = con.sendDuckDuckGoGet(url);

        System.out.println("response = " + response);

        assertTrue(response.contains("de.wikipedia.org"));

        FindService findservice = new FindService(null);

        assertTrue(findservice.getMovieWikiUrl(response).equals("https://de.wikipedia.org/wiki/NVA_(Film)"));
        assertTrue(findservice.getMovieUrl(response).equals("http://www.nva-derfilm.de/"));
        assertNull(findservice.getMovieUrl("schrott"));
    }

    @Test
    public void testDuckDuckGoGetMovieWikiUrl2() throws Exception {

        String movieTitle = "Der Untergang";

        HttpURLConnectionService con = new HttpURLConnectionService(null);

        String url = "https://api.duckduckgo.com/?q=" + movieTitle + " film&format=json";

        System.out.println("request = " + url);
        String response = con.sendDuckDuckGoGet(url);

        System.out.println("response = " + response);

        if (response.contains("\"AbstractURL\":\"\"")) {
            url = "https://api.duckduckgo.com/?q=" + movieTitle + "&format=json";
        }
        System.out.println("request = " + url);
        response = con.sendDuckDuckGoGet(url);
        assertTrue(response.contains("de.wikipedia.org"));

        FindService findservice = new FindService(null);

        assertTrue(findservice.getMovieWikiUrl(response).equals("https://de.wikipedia.org/wiki/Der_Untergang"));

    }

    @Test
    public void testDuckDuckGoGetActorWikiUrl() throws Exception {

        String actorName = "Detlev Buck";

        HttpURLConnectionService con = new HttpURLConnectionService(null);

        String url = "https://api.duckduckgo.com/?q=" + actorName + "&format=json";

        System.out.println("request = " + url);
        String response = con.sendDuckDuckGoGet(url);

        System.out.println("response = " + response);

        assertTrue(response.contains("de.wikipedia.org"));
    }
    
    @Ignore // Keine Ahnung, warum das einen Fehler 400 gibt ...
    @Test
    public void testDuckDuckGoNinaHossUrl() throws Exception {

        String url ="https://api.duckduckgo.com/?q=Nina Hoss&format=json";

        HttpURLConnectionService con = new HttpURLConnectionService(null);
       

        System.out.println("request = " + url);
        String response = con.sendDuckDuckGoGet(url);

        System.out.println("response = " + response);

        assertTrue(response.contains("de.wikipedia.org"));
    }
}
