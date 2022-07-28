package com.example.cctvstreaming;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String district;
    private String subDistrict;
    private String recognition;
    public User(String name,String district, String subDistrict, String recognition)
    {
        this.name = name;
        this.district = district;
        this.subDistrict = subDistrict;
        this.recognition = recognition;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public String getDistrict() {
        return district;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }
}
