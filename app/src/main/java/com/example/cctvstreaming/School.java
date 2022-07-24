package com.example.cctvstreaming;

import java.util.Locale;

public class School {
    private String name;
    private String link;
    public School(String name,String ip)
    {
        this.name = name;
        link = "rtmp://"+ip+":1935/live/";
        link.concat(name.toLowerCase());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
