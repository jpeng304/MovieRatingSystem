package com.cpsc411.app4;

/**
 * Created by Jonathan on 12/1/2017.
 */
public class Task {

    private float movie_rate;
    private String movie_date;
    private String movie_name;
    private int id;

    public Task(String movie_name, String movie_date, float movie_rate) {
        this.movie_rate = movie_rate;
        this.movie_date = movie_date;
        this.movie_name = movie_name;
    }
    public float getMovie_rate() {
        return movie_rate;
    }

    public void setMovie_rate(float movie_rate) {
        this.movie_rate = movie_rate;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(String movie_date) {
        this.movie_date = movie_date;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

}