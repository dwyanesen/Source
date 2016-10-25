package com.example.michael.maplocapplication;

import java.io.Serializable;

/**
 * Created by Michael on 2016/10/16.
 */
public class Friend implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String phonenum;

    public Friend(String name,String phonenum)
    {
        this.name = name;
        this.phonenum = phonenum;
    }

    public String getName(){return name;};
    public String getPhonenum(){return phonenum;};
}
