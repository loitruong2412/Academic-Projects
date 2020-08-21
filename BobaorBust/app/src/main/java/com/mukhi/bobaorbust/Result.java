package com.mukhi.bobaorbust;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a Result object which is being used to bundle a score with a date.
 */
public class Result {

    private String date;
    private int score;
    private int id;
    private long duration;


    public Result(){}


    public Result( int value, TimeCalculator timeCalculator) {
        this.score = value;
        Date todaydate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        date = formatter.format(todaydate);
        duration = timeCalculator.getTimeElapsed();
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    public String getGameDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public void setGameDate(String date) {
        this.date = date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
