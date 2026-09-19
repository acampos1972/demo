package com.example;
import java.util.List;

/**
 * Modelo de un resultado del endpoint search/shows de TVMaze.
 * La respuesta JSON es un arreglo de objetos TVSearch.
 */
public class TVSearch {
    public Double score;
    public Show show;

    public static class Show {
        public Integer id;
        public String url;
        public String name;
        public String type;
        public String language;
        public List<String> genres;
        public String status;
        public Integer runtime;
        public Integer averageRuntime;
        public String premiered;
        public String ended;
        public String officialSite;
        public Schedule schedule;
        public Rating rating;
        public Integer weight;
        public Network network;
        public WebChannel webChannel;
        public Country dvdCountry;
        public Externals externals;
        public Image image;
        public String summary;
        public Long updated;
        public Links links;
    }

    public static class Schedule {
        public String time;
        public List<String> days;
    }

    public static class Rating {
        public Double average;
    }

    public static class Network {
        public Integer id;
        public String name;
        public Country country;
        public String officialSite;
    }

    public static class WebChannel {
        public Integer id;
        public String name;
        public Country country;
        public String officialSite;
    }

    public static class Country {
        public String name;
        public String code;
        public String timezone;
    }

    public static class Externals {
        public Integer tvrage;
        public Integer thetvdb;
        public String imdb;
    }

    public static class Image {
        public String medium;
        public String original;
    }

    public static class Links {
        public Link self;
        public Link previousepisode;
        public Link nextepisode;
    }

    public static class Link {
        public String href;
        public String name;
    }
}
