package com.example.cctvstreaming;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class School implements Serializable {
    private String name;
    private String link;
    private String district,subDistrict;
    private ArrayList<String> cameraNames;
    private String ip = "65.2.11.221";
    private String principal;
    public School(String name,String district, String subDistrict,ArrayList<String> cameraNames)
    {
        this.name = name;
        this.district = district;
        this.subDistrict = subDistrict;
        this.cameraNames = cameraNames;
        link = "rtmp://"+ip+":1935/live/";
        link += this.district.replaceAll(" ","-").toLowerCase() + "-" + this.subDistrict.replaceAll(" ","-").toLowerCase() + "-";
        link += this.name.replaceAll(" ","-").toLowerCase();
        Log.d("message",link);
        this.principal = new String();

    }
    public School(String name,String district, String subDistrict,ArrayList<String> cameraNames,String link)
    {
        this.name = name;
        this.district = district;
        this.subDistrict = subDistrict;
        this.cameraNames = cameraNames;
        this.link = link;
        Log.d("message",link);
        this.principal = new String();

    }
    public School(String name,String district, String subDistrict,ArrayList<String> cameraNames,String principal,String link)
    {
        this.name = name;
        this.district = district;
        this.subDistrict = subDistrict;
        this.cameraNames = cameraNames;
        link = "rtmp://"+ip+":1935/live/";
        link += this.district.replaceAll(" ","-").toLowerCase() + "-" + this.subDistrict.replaceAll(" ","-").toLowerCase() + "-";
        link += this.name.replaceAll(" ","-").toLowerCase();
        Log.d("message",link);
        this.principal = principal;

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

    public ArrayList<String> getCameraNames() {
        return cameraNames;
    }

    public String getDistrict() {
        return district;
    }

    public String getIp() {
        return ip;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setCameraNames(ArrayList<String> cameraNames) {
        this.cameraNames = cameraNames;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

}
