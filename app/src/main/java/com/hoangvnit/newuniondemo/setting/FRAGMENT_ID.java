package com.hoangvnit.newuniondemo.setting;

/**
 * Created by hoang on 10/18/16.
 */

public enum FRAGMENT_ID {
    CITY_LIST_FRAGMENT("CITY_LIST_FRAGMENT"),
    LOGIN_FRAGMENT("LOGIN_FRAGMENT");

    private String key;

    FRAGMENT_ID(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
