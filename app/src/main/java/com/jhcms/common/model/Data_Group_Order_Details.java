package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin
 * on 2017/8/1.
 * TODO
 */

public class Data_Group_Order_Details implements Serializable {


    /**
     * order : {"order_id":"2870","shop_id":"1","order_status":"5","pay_status":"1","total_price":"10.90","money":"0.00","amount":"10.90","contact":"噼里啪啦","mobile":"13888888888","addr":"合肥市蜀山区华润五彩城国际","pay_time":"1501773908","dateline":"1501773906","comment_status":"0","tuan_id":"1","tuan_title":"方燕烤猪蹄","tuan_price":"10.90","tuan_number":"1","use_time":"0","tuan_photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","ltime":"1504195199","shop_title":"卡旺卡","phone":"13901010101","market_price":"12.00","comment_id":"0","ticket_number":"217080357260227","ticket_status":"0","qrcode":"<img alt=\"扫一扫核销\" src=\"http://o2o.jhcms.cn/qrcode.html?data=217080357260227&size=10\">"}
     */

    public OrderBean order;

    public static class OrderBean {
        /**
         * order_id : 2870
         * shop_id : 1
         * order_status : 5
         * pay_status : 1
         * total_price : 10.90
         * money : 0.00
         * amount : 10.90
         * contact : 噼里啪啦
         * mobile : 13888888888
         * addr : 合肥市蜀山区华润五彩城国际
         * pay_time : 1501773908
         * dateline : 1501773906
         * comment_status : 0
         * tuan_id : 1
         * tuan_title : 方燕烤猪蹄
         * tuan_price : 10.90
         * tuan_number : 1
         * use_time : 0
         * tuan_photo : photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg
         * ltime : 1504195199
         * shop_title : 卡旺卡
         * phone : 13901010101
         * market_price : 12.00
         * comment_id : 0
         * ticket_number : 217080357260227
         * ticket_status : 0
         * qrcode : <img alt="扫一扫核销" src="http://o2o.jhcms.cn/qrcode.html?data=217080357260227&size=10">
         */

        public String order_id;
        public String shop_id;
        public String order_status;
        public String pay_status;
        public String total_price;
        public String money;
        public double amount;
        public String contact;
        public String mobile;
        public String addr;
        public String pay_time;
        public String dateline;
        public String comment_status;
        public String tuan_id;
        public String tuan_title;
        public String tuan_price;
        public String tuan_number;
        public String use_time;
        public String tuan_photo;
        public String ltime;
        public String shop_title;
        public String phone;
        public String market_price;
        public String comment_id;
        public String ticket_number;
        public String ticket_status;
        public String qrcode;
    }
}
