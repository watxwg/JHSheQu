package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/19 10:41
 * 描述：地址信息
 */

public class AddressInfoModel {

    /**
     * addr_id : 30
     * uid : 4
     * province_name : 安徽
     * city_name : 合肥市
     * area_name : 包河区
     * contact : 小yu鱼儿
     * addr : naner
     * mobile : 15056078907
     * dateline : 1502783323
     * is_default : 1
     * province_id : 104
     * city_id : 105
     * area_id : 0
     * city_code : 0551
     */

    private String addr_id;
    private String uid;
    private String province_name;
    private String city_name;
    private String area_name;
    private String contact;
    private String addr;
    private String mobile;
    private String dateline;
    private String is_default;
    private String province_id;
    private String city_id;
    private String area_id;
    private String city_code;

    @Override
    public String toString() {
        return "AddressInfoModel{" +
                "addr_id='" + addr_id + '\'' +
                ", uid='" + uid + '\'' +
                ", province_name='" + province_name + '\'' +
                ", city_name='" + city_name + '\'' +
                ", area_name='" + area_name + '\'' +
                ", contact='" + contact + '\'' +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dateline='" + dateline + '\'' +
                ", is_default='" + is_default + '\'' +
                ", province_id='" + province_id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", area_id='" + area_id + '\'' +
                ", city_code='" + city_code + '\'' +
                '}';
    }

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }
}
