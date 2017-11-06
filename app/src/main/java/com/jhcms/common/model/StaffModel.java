package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/15.
 */
public class StaffModel implements Serializable {
    /**
     * staff_id : 0
     */

    private String staff_id;
    private  String mobile;


    private String name;
    private  String face;


    private String lat;
    private  String lng;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }
}
