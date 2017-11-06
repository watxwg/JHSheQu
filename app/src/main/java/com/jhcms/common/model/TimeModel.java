package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/14.
 */
public class TimeModel implements Serializable {


    /**
     * date : 11:44
     * minute : 10
     */

    private String date;
    private String minute;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
