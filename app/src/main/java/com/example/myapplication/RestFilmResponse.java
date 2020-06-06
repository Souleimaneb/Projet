package com.example.myapplication;

import java.util.List;

public class RestFilmResponse {
    private Integer count;
    private String next;
    private List<Film> results;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List<Film> getResults() {
        return results;
    }
}
