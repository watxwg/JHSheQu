package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/19 11:24
 * 描述：订单中的优惠券信息
 */

public class CouponOfOrderModel {

    /**
     * cid : 37
     * coupon_id : 13
     * uid : 59
     * title : 优惠大酬宾
     * use_time : 0
     * order_id : 0
     * status : 0
     * dateline : 1505785193
     * ltime : 1506389993
     * order_amount : 50.00
     * coupon_amount : 5.00
     * shop_id : 7
     */

    private String cid;
    private String coupon_id;
    private String uid;
    private String title;
    private String use_time;
    private String order_id;
    private String status;
    private String dateline;
    private String ltime;
    private String order_amount;
    private String coupon_amount;
    private String shop_id;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getLtime() {
        return ltime;
    }

    public void setLtime(String ltime) {
        this.ltime = ltime;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
}
