package com.jhcms.common.model;

import java.util.ArrayList;

/**
 * Created by admin on 2017/6/24.
 */
public class Data_Balancemodel {
    private ArrayList<Balancemodel> items;

    public ArrayList<Balancemodel> getItems() {
        return items;
    }

    public void setItems(ArrayList<Balancemodel> items) {
        this.items = items;
    }
    private  String money;
    private String total_count;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }
}
