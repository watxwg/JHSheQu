package com.jhcms.run.mode;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 17:04
 * 描述：选择下单类型
 */

public class SelectTypeInfoModel {
    private AddressInfoModel addr;
    private CateInfoModel cate;
    private String pei_amount;
    private TimeInfoModel time;
    private List<DayConfigInfoModel> day_config;
    private List<HongbaoInfoModel> hongbao;
    private String weight;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public AddressInfoModel getAddr() {
        return addr;
    }

    public void setAddr(AddressInfoModel addr) {
        this.addr = addr;
    }

    public CateInfoModel getCate() {
        return cate;
    }

    public void setCate(CateInfoModel cate) {
        this.cate = cate;
    }

    public String getPei_amount() {
        return pei_amount;
    }

    public void setPei_amount(String pei_amount) {
        this.pei_amount = pei_amount;
    }

    public TimeInfoModel getTime() {
        return time;
    }

    public void setTime(TimeInfoModel time) {
        this.time = time;
    }

    public List<DayConfigInfoModel> getDay_config() {
        return day_config;
    }

    public void setDay_config(List<DayConfigInfoModel> day_config) {
        this.day_config = day_config;
    }

    public List<HongbaoInfoModel> getHongbao() {
        return hongbao;
    }

    public void setHongbao(List<HongbaoInfoModel> hongbao) {
        this.hongbao = hongbao;
    }
}
