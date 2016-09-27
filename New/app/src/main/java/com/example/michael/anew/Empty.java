package com.example.michael.anew;

import java.io.Serializable;

/**
 * Created by Michael on 2016/9/18.
 */
public class Empty implements Serializable {

    private static final long serialVersionUID = 1L;

    private int empty;
    private String day;
    private String week;
    private String month;
    private String year;

    public Empty(int empty,String week, String day,String month,String year)
    {
        this.day = day;
        this.week = week;
        this.empty = empty;
        this.year = year;
        this.month = month;
    }

    public String getDay(){return day;}
    public String getWeek(){return week;}
    public int getEmpty() {return empty;}
    public String getMonth(){return month;}
    public String getYear(){return year;}
}