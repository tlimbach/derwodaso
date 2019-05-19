/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derwodaso.main.service;

import derwodaso.main.model.Actor;
import java.util.ArrayList;
import java.util.List;
import derwodaso.main.model.Caracter;
import derwodaso.main.model.Movie;
import java.awt.Container;
import java.net.URL;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tlimbach
 */
public class FindService {

    private HttpURLConnectionService connService;

    private String apiKey = "";
    private String baseUrl = "https://api.themoviedb.org/3/search/";

    public FindService(Container container) {
        connService = new HttpURLConnectionService(container);
    }

    public List<Caracter> parseResultForActorsByMovie(Movie movie, String result) throws ParseException {
        List<Caracter> characters = new ArrayList<>();

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result);
        JSONObject j2 = (JSONObject) json.get("credits");
        JSONArray j3 = (JSONArray) j2.get("cast");

        for (int t = 0; t < j3.size(); t++) {
            JSONObject actObj = (JSONObject) j3.get(t);
            String character = (String) actObj.get("character");
            String name = (String) actObj.get("name");
            String profilePath = (String) actObj.get("profile_path");
            Long id = (Long) actObj.get("id");

            Actor actor = new Actor(name, id, profilePath);

            characters.add(new Caracter(character, actor));
        }

        return characters;
    }

    public List<Movie> parseMovieQueryResultForMovies(String result) throws ParseException {
        List<Movie> movies = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result);

        JSONArray results = (JSONArray) json.get("results");

        for (int t = 0; t < results.size(); t++) {
            JSONObject mvObj = (JSONObject) results.get(t);
            Movie movie = createMovieObject(mvObj);
            movies.add(movie);
        }

        return movies;
    }

    private List<Actor> parsePersonQueryResultForPersons(String result) throws ParseException {
        List<Actor> actors = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result);

        JSONArray results = (JSONArray) json.get("results");

        for (int t = 0; t < results.size(); t++) {
            JSONObject acObj = (JSONObject) results.get(t);
            Actor actor = createActorObject(acObj);
            actors.add(actor);
        }
        return actors;
    }

    public List<Movie> parseResultForMoviesByActor(String result) throws ParseException {
        List<Movie> movies = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result);
        JSONArray results = (JSONArray) json.get("cast");

        for (int t = 0; t < results.size(); t++) {
            JSONObject mvObj = (JSONObject) results.get(t);
            Movie movie = createMovieObject(mvObj);

//            System.out.println("ccc---> " + character);
            movies.add(movie);
        }

        return movies;
    }

    public Movie createMovieObject(JSONObject mvObj) {
        String title = (String) mvObj.get("title");
        String posterPath = (String) mvObj.get("poster_path");
        Long id = (Long) mvObj.get("id");
        Movie movie = new Movie(title, posterPath, id);
        String relDate = (String) mvObj.get("release_date");
        String backdropPath = (String) mvObj.get("backdrop_path");
        String character = (String) mvObj.get("character");
        movie.setReleaseDate(relDate);
        movie.setBackdropPath(backdropPath);
        movie.setCharacter(character);
        return movie;
    }

    private Actor createActorObject(JSONObject acObj) {
        String name = (String) acObj.get("name");
        Long id = (Long) acObj.get("id");
        String profilePath = (String) acObj.get("profile_path");
        return new Actor(name, id, profilePath);
    }

    public List<Movie> searchMovies(String name) throws Exception {
        String url = baseUrl + "movie?api_key=" + apiKey + "&query=" + name;
        String res = connService.sendGet(url);

        List<Movie> movies = parseMovieQueryResultForMovies(res);

        return movies;
    }

    public List<Actor> searchActors(String name) throws Exception {
        String url = baseUrl + "person?api_key=" + apiKey + "&query=" + name;
        String res = connService.sendGet(url);

        List<Actor> actors = parsePersonQueryResultForPersons(res);

        return actors;
    }

    public List<Caracter> searchCharacters(Movie movie) throws Exception {
        String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "?api_key=" + apiKey + "&append_to_response=credits,videos";
        String res = connService.sendGet(url);

        return parseResultForActorsByMovie(movie, res);
    }

    public List<Movie> searchMoviesByActor(Caracter character) throws Exception {

        String url = "https://api.themoviedb.org/3/person/" + character.getActor().getId() + "/movie_credits?api_key=" + apiKey;
        String res = connService.sendGet(url);

        return parseResultForMoviesByActor(res);
    }

    public URL searchMovieUrl(Movie movie) {
        try {
            String req = "https://api.duckduckgo.com/?q=" + movie.getTitle() + " film&format=json";
            final String jsonString = connService.sendDuckDuckGoGet(req);

            String movieUrl = getMovieUrl(jsonString);

            return new URL(movieUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL searchMovieWikiUrl(Movie movie) {
        try {
            String request = "https://api.duckduckgo.com/?q=" + movie.getTitle() + " film&format=json";
            String response = connService.sendDuckDuckGoGet(request);

            System.out.println("request = " + request);

            System.out.println("response = " + response);

            if (response.contains("\"AbstractURL\":\"\"")) {
                request = "https://api.duckduckgo.com/?q=" + movie.getTitle() + "&format=json";
            }
            System.out.println("request = " + request);
            response = connService.sendDuckDuckGoGet(request);

            String wikiMovieUrl = getMovieWikiUrl(response);

            return new URL(wikiMovieUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL searchActorWikiUrl(Actor actor) {
        try {
            String req = "https://api.duckduckgo.com/?q=" + actor.getName() + "&format=json";
            final String jsonString = connService.sendDuckDuckGoGet(req);

            String wikiMovieUrl = getActorWikiUrl(jsonString);

            return new URL(wikiMovieUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMovieWikiUrl(String jsonString) throws ParseException {
        String url = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            url = (String) json.get("AbstractURL");
        } catch (Exception e) {
            // egal
        }
        return url;
    }

    private String getActorWikiUrl(String jsonString) {
        String url = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            url = (String) json.get("AbstractURL");
        } catch (Exception e) {
            // egal
        }
        return url;
    }

    public String getMovieUrl(String jsonString) {
        String url = null;
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            JSONArray results = (JSONArray) json.get("Results");
            JSONObject resultOne = (JSONObject) results.get(0);
            url = (String) resultOne.get("FirstURL");
        } catch (Exception e) {
            // egal, dann keine Url für den Film...
        }
        return url;
    }

}
