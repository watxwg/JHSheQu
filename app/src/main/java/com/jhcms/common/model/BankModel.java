package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/9/15.
 */
public class BankModel implements Serializable {

    /**
     * uid : 23
     * card_id : 22
     * card_name : 测试
     * card_type : 1
     * card_number : 4242424242424242
     * exp_month : 12
     * exp_year : 2020
     * cvc : 888
     * dateline : 1505471705
     * card_label : ****  ****  **** 4242
     */

    private String uid;
    private String card_id;
    private String card_name;
    private String card_type;
    private String card_number;
    private String exp_month;
    private String exp_year;
    private String cvc;
    private String dateline;
    private String card_label;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getCard_label() {
        return card_label;
    }

    public void setCard_label(String card_label) {
        this.card_label = card_label;
    }
}
