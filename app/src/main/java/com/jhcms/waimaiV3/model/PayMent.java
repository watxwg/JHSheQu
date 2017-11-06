package com.jhcms.waimaiV3.model;

/**
 * Created by wangyujie
 * Date 2017/6/13.
 * TODO 支付方式
 */

public class PayMent {
    public String online_pay;
    public String is_daofu;

    public PayMent(String online_pay, String is_daofu) {
        this.online_pay = online_pay;
        this.is_daofu = is_daofu;
    }

}

