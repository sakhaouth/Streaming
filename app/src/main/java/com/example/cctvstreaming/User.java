package com.example.cctvstreaming;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String district;
    private String subDistrict;
    private String recognition;
    private String number;
    private String status;
    private String id;
    private String email;
    public User(String name,String district, String subDistrict, String recognition,String number,String id,String email)
    {
        this.name = name;
        this.district = district;
        this.subDistrict = subDistrict;
        this.recognition = recognition;
        this.number = number;
        this.status = "ok";
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
