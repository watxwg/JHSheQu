package com.jhcms.run.mode;

import java.io.Serializable;

/**
 * 作者：WangWei
 * 日期：2017/10/10 16:58
 * 描述：
 */

public class AddressInfoModel implements Serializable{

    /**
     * addr_id : 12
     * addr : 华邦伊赛特广场
     * house : 123
     * lat : 31.835364
     * lng : 117.237421
     * group_id : 2
     */

    private String addr_id;
    private String addr;
    private String house;
    private String lat;
    private String lng;
    private String group_id;
    /**
     * contact : 利息人
     * mobile : 18226618664
     * type : 1
     * is_default : 1
     * is_available : 1
     * addr_label : 公司
     */

    private String contact;
    private String mobile;
    private String type;
    private String is_default;
    private String is_available;
    private String addr_label;

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
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

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getIs_available() {
        return is_available;
    }

    public void setIs_available(String is_available) {
        this.is_available = is_available;
    }

    public String getAddr_label() {
        return addr_label;
    }

    public void setAddr_label(String addr_label) {
        this.addr_label = addr_label;
    }
}
