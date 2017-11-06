package com.jhcms.mall.model;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/19 10:36
 * 描述：店铺订单信息
 */

public class ShopOrderInfoModel {

    /**
     * total_price : 99
     * total_number : 1
     * freight : 0
     * coupon_id : 0
     * coupon :
     * coupons : []
     * coupon_num : 0
     */
    private String shop_id;
    private String total_price;
    private String total_number;
    private double freight;
    private String coupon_id;
    private CouponOfOrderModel coupon;
    private String coupon_num;
    //用于记录备注信息
    private String markInfo;
    private List<CouponOfOrderModel> coupons;
    private HashMap<String, ProductOrderInfo> cart_goods;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getMarkInfo() {
        return markInfo;
    }

    public void setMarkInfo(String markInfo) {
        this.markInfo = markInfo;
    }

    public HashMap<String, ProductOrderInfo> getCart_goods() {
        return cart_goods;
    }

    public void setCart_goods(HashMap<String, ProductOrderInfo> cart_goods) {
        this.cart_goods = cart_goods;
    }


    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public CouponOfOrderModel getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponOfOrderModel coupon) {
        this.coupon = coupon;
    }

    public String getCoupon_num() {
        return coupon_num;
    }

    public void setCoupon_num(String coupon_num) {
        this.coupon_num = coupon_num;
    }

    public List<CouponOfOrderModel> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponOfOrderModel> coupons) {
        this.coupons = coupons;
    }

    /**
     * 商品订单信息
     */
    public static class ProductOrderInfo {

        /**
         * product_id : 25
         * number : 1
         * stock_name : 0
         * photo : photo/201706/20170603_F27ACF19188983B6A4A2E0E2BDAE8117.jpg
         * wei_price : 50.00
         * freight_type : 1
         * title : 测试商品55
         * freight : 0.00
         * not_pei : 1
         * true_freight : 0
         */

        private String product_id;
        private String number;
        private String stock_name;
        private String photo;
        private String wei_price;
        private String freight_type;
        private String title;
        private String freight;
        private String not_pei;
        private String true_freight;
        private String stock_real_name;

        public String getStock_real_name() {
            return stock_real_name;
        }

        public void setStock_real_name(String stock_real_name) {
            this.stock_real_name = stock_real_name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getStock_name() {
            return stock_name;
        }

        public void setStock_name(String stock_name) {
            this.stock_name = stock_name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getWei_price() {
            return wei_price;
        }

        public void setWei_price(String wei_price) {
            this.wei_price = wei_price;
        }

        public String getFreight_type() {
            return freight_type;
        }

        public void setFreight_type(String freight_type) {
            this.freight_type = freight_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getNot_pei() {
            return not_pei;
        }

        public void setNot_pei(String not_pei) {
            this.not_pei = not_pei;
        }

        public String getTrue_freight() {
            return true_freight;
        }

        public void setTrue_freight(String true_freight) {
            this.true_freight = true_freight;
        }
    }
}
