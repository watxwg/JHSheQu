package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/6/12.
 */
public class ShopOrderModel implements Serializable {


    /**
     * order_id : 431
     * shop_id : 1
     * staff_id : 2
     * uid : 4
     * order_status : 2
     * pay_status : 1
     * total_price : 23.00
     * money : 0.00
     * amount : 21.00
     * pei_type : 1
     * dateline : 1497320546
     * comment_status : 0
     * msg : 骑手已接单
     * shop_title : 卡旺卡
     * shop_logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
     * order_status_label : 等待配送
     * refund : -1
     * product_number : 2
     * product_price : 16.00
     * package_price : 4.00
     * freight : 3.00
     * spend_number :
     * spend_status : 0
     * product_title : 测试商品2等2件商品
     */
    public String jifen_total;
    public  String cui_time;
    public String order_id;
    public String shop_id;
    public String staff_id;
    public String uid;
    public String order_status;
    public String pay_status;
    public String total_price;
    public String money;
    public String amount;
    public String pei_type;
    public String dateline;
    public String comment_status;
    public String msg;
    public String shop_title;
    public String shop_logo;
    public String order_status_label;
    public String refund;
    public String product_number;
    public String product_price;
    public String package_price;
    public String freight;
    public String spend_number;
    public String spend_status;
    public String product_title;
    public  String pay_ltime;
    public  String comment_id;
    public  String online_pay;

    public String getOnline_pay() {
        return online_pay;
    }

    public void setOnline_pay(String online_pay) {
        this.online_pay = online_pay;
    }

    public String getPay_ltime() {
        return pay_ltime;
    }

    public void setPay_ltime(String pay_ltime) {
        this.pay_ltime = pay_ltime;
    }

    public ArrayList<ProductModle> products;
    public ArrayList<TimeModel> time;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
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

    public String getPei_type() {
        return pei_type;
    }

    public void setPei_type(String pei_type) {
        this.pei_type = pei_type;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getOrder_status_label() {
        return order_status_label;
    }

    public void setOrder_status_label(String order_status_label) {
        this.order_status_label = order_status_label;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getCui_time() {
        return cui_time;
    }

    public void setCui_time(String cui_time) {
        this.cui_time = cui_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
