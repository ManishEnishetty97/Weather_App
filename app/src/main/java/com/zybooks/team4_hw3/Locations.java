package com.zybooks.team4_hw3;

/**
 * author  Manish Enishetty
 * CID     C22384538
 * MailID  menishe@clemson.edu
 */
public class Locations {
    private int cityId;
    private String cityName;
    private String cityCode;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Locations(int cityId, String cityName,String cityCode) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityCode=cityCode;
    }
}
