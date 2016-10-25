package com.example.michael.maplocapplication;

import java.io.Serializable;

/**
 * Created by Michael on 2016/10/22.
 */
public class Enemy implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String phonenum;
    private String feature;

    public Enemy(String name,String phonenum,String feature)
    {
        this.name = name;
        this.phonenum = phonenum;
        this.feature = feature;
    }

    public String getName(){return name;};
    public String getPhonenum(){return phonenum;};

}
