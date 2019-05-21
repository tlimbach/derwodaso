package derwodaso.main.service;

import java.awt.Container;
import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionService {

    Map<String, String> map = new HashMap<>();

    public static boolean useCache = true;

    private static int cacheCounter = 0;
    private static int noCacheCounter = 0;
    private final Container container;

    public HttpURLConnectionService(Container container) {
        this.container = container;
    }

    public synchronized String sendDuckDuckGoGet(String urlAsString) throws IOException {
        String responeAsString = null;

        try {
            String url = null;

            urlAsString = urlAsString.replaceAll("[()-]", "");

            if (map.get(urlAsString) != null) {
                cacheCounter++;
//                System.out.println("cache counter " + cacheCounter);
                if (container != null) {
                    container.setCursor(Cursor.getDefaultCursor());
                }
                return map.get(urlAsString);
            }
   if (container != null) {
                container.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
            URL obj = new URL(urlAsString);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "de-DE");


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + urlAsString);

            if (responseCode != 200) {
                System.out.println("Response Code : " + responseCode);
            }

            StringBuffer response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            responeAsString = response.toString();
            if (responseCode != 200) {
                System.out.println("res:" + responeAsString);
            }
            if (container != null) {
                container.setCursor(Cursor.getDefaultCursor());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // schade schade...
               if (container != null) {
                container.setCursor(Cursor.getDefaultCursor());
            }
        }
        return responeAsString;
    }

    public synchronized String sendGet(String urlAsString) throws Exception {
        String responeAsString = null;
        try {
            long start = System.currentTimeMillis();
            urlAsString += "&language=de";
            urlAsString = urlAsString.replaceAll(" ", "%20");

            if (map.get(urlAsString) != null) {
                cacheCounter++;
//                System.out.println("cache counter " + cacheCounter);
                return map.get(urlAsString);
            }

            container.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        url = url.replaceAll("ß", "%c3");
            URL obj = new URL(urlAsString);
            int responseCode = 0;
            HttpURLConnection con = null;
            for (int t = 0; t < 3; t++) {
                con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Language", "de-DE");

                responseCode = con.getResponseCode();
                String mainigRequests = con.getHeaderField("X-RateLimit-Remaining");
                System.out.println("remaining request " + mainigRequests);

                if (responseCode == 500) {
                    System.out.println("Error 500 ... waiting to retry " + t);
                    Thread.sleep(1000);
                } else {
                    break;
                }

            }
            System.out.println("\nSending 'GET' request to URL : " + urlAsString);

            if (responseCode != 200) {
                System.out.println("Response Codi: " + responseCode);
            }

            StringBuffer response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"))) {
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            responeAsString = response.toString();

            if (responseCode != 200) {
                System.out.println("res:" + responeAsString);
            }

            if (useCache) {
                map.put(urlAsString, responeAsString);
            }

            noCacheCounter++;
//            System.out.println("noCacheCounter " + noCacheCounter);
//            System.out.println("Request took: " + (System.currentTimeMillis() - start) + " ms");
            container.setCursor(Cursor.getDefaultCursor());
        } catch (Exception e) {
            container.setCursor(Cursor.getDefaultCursor());
            e.printStackTrace();
        }
        return responeAsString;

    }

    public String cleanUrl(String url) {
        return "x";
    }

}
