package com.hoangvnit.newuniondemo.mvp.model;

import java.util.Date;

/**
 * Created by hoang on 10/18/16.
 */

public class Organization extends BaseModel {
    private String id;
    private String organizationName;
    private int pinCode;
    private String address;
    private String country;
    private String state;
    private String city;
    private Date time;

    public Organization() {
    }

    public Organization(String organizationName, int pinCode, String address, String country, String state, String city) {
        this.organizationName = organizationName;
        this.pinCode = pinCode;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
