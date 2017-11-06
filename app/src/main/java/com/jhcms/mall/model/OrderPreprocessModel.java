package com.jhcms.mall.model;

import java.util.HashMap;

/**
 * 作者：WangWei
 * 日期：2017/9/19 10:44
 * 描述：订单预处理返回的数据
 */

public class OrderPreprocessModel {
    private HashMap<String,ShopOrderInfoModel> order_info;
    private String money;
    private HashMap<String,AddressInfoModel> addrs;
    private String intro;
    private AddressInfoModel addr;

    @Override
    public String toString() {
        return "OrderPreprocessModel{" +
                "order_info=" + order_info.size() +
                ", money='" + money + '\'' +
                ", addrs=" + addrs.size() +
                ", intro='" + intro + '\'' +
                ", addr=" + addr.toString() +
                '}';
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public HashMap<String, AddressInfoModel> getAddrs() {
        return addrs;
    }

    public void setAddrs(HashMap<String, AddressInfoModel> addrs) {
        this.addrs = addrs;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public AddressInfoModel getAddr() {
        return addr;
    }

    public void setAddr(AddressInfoModel addr) {
        this.addr = addr;
    }

    public HashMap<String, ShopOrderInfoModel> getOrder_info() {
        return order_info;
    }

    public void setOrder_info(HashMap<String, ShopOrderInfoModel> order_info) {
        this.order_info = order_info;
    }
}
