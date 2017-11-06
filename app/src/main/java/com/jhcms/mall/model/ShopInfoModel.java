package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/13 11:37
 * 描述：店铺信息
 */

public class ShopInfoModel {


    /**
     * shop_id : 1
     * title : 卡旺卡(商城)
     * logo : photo/201705/20170509_EC326F962147ADAE552155FE04DD57CC.jpg
     * collects : 6
     * collect : 0
     */

    private String shop_id;
    private String title;
    private String logo;
    private String collects;
    private String collect;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCollects() {
        return collects;
    }

    public void setCollects(String collects) {
        this.collects = collects;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }
}
