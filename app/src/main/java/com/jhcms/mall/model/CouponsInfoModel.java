package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/13 11:39
 * 描述：优惠券信息
 */

public class CouponsInfoModel {

    /**
     * coupon_id : 11
     * title : 6.13优惠券
     * order_amount : 50.00
     * coupon_amount : 15.00
     * status : 1
     */

    private String coupon_id;
    private String title;
    private String order_amount;
    private String coupon_amount;
    private String status;

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
