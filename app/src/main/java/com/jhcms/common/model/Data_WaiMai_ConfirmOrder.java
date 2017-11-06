package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/12.
 * TODO
 */

public class Data_WaiMai_ConfirmOrder implements Serializable {

    /**
     * products : 1:0:1,2:0:1,10:0:1
     * amount : 14.01
     * product_number : 3
     * product_price : 17.01
     * online_pay : 1
     * is_daofu : 0
     * is_ziti : 1
     * package_price : 4
     * freight_stage : 3
     * youhui : [{"title":"首单立减","word":"首","color":"20AD20","amount":"10"}]
     * coupon_id : 0
     * coupon_amount : 0
     * coupons : []
     * hongbao_id : 0
     * hongbao_amount : 0
     * hongbaos : []
     * product_lists : [{"title":"测试商品1","spec_id":"0","spec_name":"","num":"1","price":"9.00"},{"title":"测试商品2","spec_id":"0","spec_name":"","num":"1","price":"8.00"},{"title":"测试商品4","spec_id":"0","spec_name":"","num":"1","price":"0.01"}]
     * shop_id : 1
     * shop_title : 卡旺卡
     * shop_addr : 五彩国际
     * shop_lng : 117.257184
     * shop_lat : 31.835824
     * m_addr : {"addr_id":"91","uid":"59","contact":"kjhjlkjl","mobile":"13899990000","addr":"华润五彩城","house":"00","lat":"31.835828","dateline":"1497246123","lng":"117.257186","type":"1","is_default":"0","min_price":"10","shipping_fee":"3"}
     * day_dates : [{"date":"2017-06-12","day":"今天(周一)"},{"date":"2017-06-13","day":"明天(周二)"}]
     * set_time_date : {"set_time":["19:15","19:30","19:45","20:00","20:15","20:30","20:45","21:00"],"nomal_time":["8:15","8:30","8:45","9:00","9:15","9:30","9:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45","12:00","12:15","12:30","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45","19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45","21:00"]}
     * mymoney : 1.00
     */

    public String products;
    public String amount;
    public String product_number;
    public String product_price;
    public String online_pay;
    public String is_daofu;
    public int is_ziti;
    public String package_price;
    public String freight_stage;
    public int coupon_id;
    public String coupon_amount;
    public int hongbao_id;
    public String hongbao_amount;
    public String shop_id;
    public String shop_title;
    public String shop_addr;
    public String shop_lng;
    public String shop_lat;
    public MAddrEntity m_addr;
    public SetTimeDateEntity set_time_date;
    public String mymoney;
    public List<YouhuiEntity> youhui;
    public List<CouponsEntity> coupons;
    public List<HongbaosEntity> hongbaos;
    public List<ProductListsEntity> product_lists;
    public List<DayDatesEntity> day_dates;


    public static class MAddrEntity {
        /**
         * addr_id : 91
         * uid : 59
         * contact : kjhjlkjl
         * mobile : 13899990000
         * addr : 华润五彩城
         * house : 00
         * lat : 31.835828
         * dateline : 1497246123
         * lng : 117.257186
         * type : 1
         * is_default : 0
         * min_price : 10
         * shipping_fee : 3
         */

        public String addr_id;
        public String uid;
        public String contact;
        public String mobile;
        public String addr;
        public String house;
        public String lat;
        public String dateline;
        public String lng;
        public String type;
        public String is_default;
        public String min_price;
        public String shipping_fee;

    }

    public static class SetTimeDateEntity {
        public List<String> set_time;
        public List<String> nomal_time;

    }

    public static class YouhuiEntity {
        /**
         * title : 首单立减
         * word : 首
         * color : 20AD20
         * amount : 10
         */

        public String title;
        public String word;
        public String color;
        public String amount;

    }

    public static class HongbaosEntity {
        public int hongbao_id;
        public String min_amount;
        public String amount;
    }

    public static class CouponsEntity {
        public int coupon_id;
        public String order_amount;
        public String coupon_amount;
    }

    public static class ProductListsEntity {
        /**
         * title : 测试商品1
         * spec_id : 0
         * spec_name :
         * num : 1
         * price : 9.00
         */

        public String title;
        public String spec_id;
        public String spec_name;
        public String num;
        public String price;


    }

    public static class DayDatesEntity {
        /**
         * date : 2017-06-12
         * day : 今天(周一)
         */

        public String date;
        public String day;

    }
}
