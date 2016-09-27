package com.example.michael.anew;

import java.io.Serializable;

/**
 * Created by Michael on 2016/9/16.
 */

public class Time implements Serializable {

    private static final long serialVersionUID = 1L;

    private String day;
    private String week;
    private String event;
    private String year;
    private String month;

    public Time(String week, String day, String event,String month,String year)
    {
        this.day = day;
        this.week = week;
        this.event = event;
        this.month = month;
        this.year = year;
    }

    public String getDay()
    {
        return day;
    }

    public String getWeek()
    {
        return week;
    }

    public String getEvent()
    {
        return event;
    }

    public String getMonth()
    {
        return month;
    }

    public String getYear()
    {
        return year;
    }

    public void setEvent(String event){this.event = event; }
}
