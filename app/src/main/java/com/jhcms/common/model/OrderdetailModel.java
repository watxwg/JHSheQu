package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/6/15.
 */
public class OrderdetailModel implements Serializable{
    private StaffModel staff;
    private  WaimaiModel waimai;
    private ArrayList<ProductModle> products;
    private  ArrayList<YouHuiModel> youhui;
    private hongbaoModel hongbao;
    private  ArrayList<TimeModel> time;

    public StaffModel getStaff() {
        return staff;
    }

    public void setStaff(StaffModel staff) {
        this.staff = staff;
    }

    public WaimaiModel getWaimai() {
        return waimai;
    }

    public void setWaimai(WaimaiModel waimai) {
        this.waimai = waimai;
    }

    public ArrayList<ProductModle> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModle> products) {
        this.products = products;
    }

    public ArrayList<YouHuiModel> getYouhui() {
        return youhui;
    }

    public void setYouhui(ArrayList<YouHuiModel> youhui) {
        this.youhui = youhui;
    }

    public hongbaoModel getHongbao() {
        return hongbao;
    }

    public void setHongbao(hongbaoModel hongbao) {
        this.hongbao = hongbao;
    }

    public ArrayList<TimeModel> getTime() {
        return time;
    }

    public void setTime(ArrayList<TimeModel> time) {
        this.time = time;
    }


    /**
     * order_id : 833
     * staff_id : 0
     * uid : 4
     * order_status : 0
     * pay_status : 1
     * total_price : 22.00
     * money : 0.00
     * amount : 19.00
     * contact : 北辰灬陨落
     * mobile : 17756570802
     * addr :
     * house :
     * lng : 0
     * lat : 0
     * intro :
     * order_from : wap
     * pay_code : money
     * pay_time : 1498007800
     * pei_time : 0
     * pei_type : 3
     * dateline : 1498007796
     * comment_status : 0
     * cui_time : 0
     * product_price : 20.00
     * package_price : 2.00
     * freight : 0.00
     * spend_number : 117062174597087
     * spend_status : 0
     * msg : 等待商户接单
     * order_status_label : 待接单
     * refund : -1
     * jifen_total : 19
     * kefu_phone : 0551-64278115
     * payment_type : 现金支付
     * complaint : 0
     * pay_ltime : 15
     * comment_id : 0
     * link : http://waimai.o2o.jhcms.cn/ucenter/order/status-833.html
     */

    private String order_id;
    private String staff_id;
    private String uid;
    private String order_status;
    private String pay_status;
    private String total_price;
    private String money;
    private String amount;
    private String contact;
    private String mobile;
    private String addr;
    private String house;
    private String lng;
    private String lat;
    private String intro;
    private String order_from;
    private String pay_code;
    private String pay_time;
    private String pei_time;
    private String pei_type;
    private String dateline;
    private String comment_status;
    private String cui_time;
    private String product_price;
    private String package_price;
    private String freight;
    private String spend_number;
    private String spend_status;
    private String msg;
    private String order_status_label;
    private String refund;
    private String jifen_total;
    private String kefu_phone;
    private String payment_type;
    private String complaint;
    private String pay_ltime;
    private String comment_id;
    private String link;
    public   String online_pay;

    public String getOnline_pay() {
        return online_pay;
    }

    public void setOnline_pay(String online_pay) {
        this.online_pay = online_pay;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getOrder_from() {
        return order_from;
    }

    public void setOrder_from(String order_from) {
        this.order_from = order_from;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPei_time() {
        return pei_time;
    }

    public void setPei_time(String pei_time) {
        this.pei_time = pei_time;
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

    public String getCui_time() {
        return cui_time;
    }

    public void setCui_time(String cui_time) {
        this.cui_time = cui_time;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getSpend_number() {
        return spend_number;
    }

    public void setSpend_number(String spend_number) {
        this.spend_number = spend_number;
    }

    public String getSpend_status() {
        return spend_status;
    }

    public void setSpend_status(String spend_status) {
        this.spend_status = spend_status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getJifen_total() {
        return jifen_total;
    }

    public void setJifen_total(String jifen_total) {
        this.jifen_total = jifen_total;
    }

    public String getKefu_phone() {
        return kefu_phone;
    }

    public void setKefu_phone(String kefu_phone) {
        this.kefu_phone = kefu_phone;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getPay_ltime() {
        return pay_ltime;
    }

    public void setPay_ltime(String pay_ltime) {
        this.pay_ltime = pay_ltime;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
