package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/25.
 */
public class GroupOfferToPayOrderDeatailData implements Serializable {


    /**
     * order : {"order_id":"2717","shop_id":"1","order_status":"-1","total_price":"10.00","money":"0.00","amount":"9.50","contact":"哈哈","mobile":"18756908065","addr":"合肥市蜀山区华润五彩城国际","pay_time":"0","dateline":"1500948485","comment_status":"0","maidan_amount":"10.00","unyouhui":"0.00","shop_title":"卡旺卡","shop_logo":"photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg","phone":"13901010101","comment_id":"0"}
     */

    private OrderBean order;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * order_id : 2717
         * shop_id : 1
         * order_status : -1
         * total_price : 10.00
         * money : 0.00
         * amount : 9.50
         * contact : 哈哈
         * mobile : 18756908065
         * addr : 合肥市蜀山区华润五彩城国际
         * pay_time : 0
         * dateline : 1500948485
         * comment_status : 0
         * maidan_amount : 10.00
         * unyouhui : 0.00
         * shop_title : 卡旺卡
         * shop_logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
         * phone : 13901010101
         * comment_id : 0
         */

        private String order_id;
        private String shop_id;
        private String order_status;
        private String total_price;
        private String money;
        private String amount;
        private String contact;
        private String mobile;
        private String addr;
        private String pay_time;
        private String dateline;
        private String comment_status;
        private String maidan_amount;
        private String unyouhui;
        private String shop_title;
        private String shop_logo;
        private String phone;
        private String comment_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getComment_status() {
            return comment_status;
        }

        public void setComment_status(String comment_status) {
            this.comment_status = comment_status;
        }

        public String getMaidan_amount() {
            return maidan_amount;
        }

        public void setMaidan_amount(String maidan_amount) {
            this.maidan_amount = maidan_amount;
        }

        public String getUnyouhui() {
            return unyouhui;
        }

        public void setUnyouhui(String unyouhui) {
            this.unyouhui = unyouhui;
        }

        public String getShop_title() {
            return shop_title;
        }

        public void setShop_title(String shop_title) {
            this.shop_title = shop_title;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }
    }
}
