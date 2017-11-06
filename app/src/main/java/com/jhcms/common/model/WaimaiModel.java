package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/15.
 */
public class WaimaiModel implements Serializable {
    /**
     * shop_id : 1
     * title : 卡旺卡
     * phone : 13901010101
     * lat : 31.835824
     * lng : 117.257184
     * logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
     * addr : 五彩国际
     */

    private String shop_id;
    private String title;
    private String phone;
    private String lat;
    private String lng;
    private String logo;
    private String addr;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
