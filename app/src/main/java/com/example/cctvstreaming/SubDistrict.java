package com.example.cctvstreaming;

public class SubDistrict {
    private String name;
    private String UNO;
    public SubDistrict(String name)
    {
        this.name = name;
        this.UNO = new String();
    }
    public SubDistrict(String name,String UNO)
    {
        this.name = name;
        this.UNO = UNO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUNO() {
        return UNO;
    }

    public void setUNO(String UNO) {
        this.UNO = UNO;
    }

}
