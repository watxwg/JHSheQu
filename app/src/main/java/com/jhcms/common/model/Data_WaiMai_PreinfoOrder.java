package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/17.
 * TODO 订单预处理
 */

public class Data_WaiMai_PreinfoOrder implements Serializable {

    /**
     * products : 2:0:31
     * amount : 283
     * product_number : 31
     * product_price : 248
     * online_pay : 1
     * is_daofu : 0
     * is_ziti : 1
     * package_price : 62
     * freight_stage : 3
     * youhui : [{"title":"首单立减","word":"首","color":"20AD20","amount":"10"},{"title":"满100减20","word":"减","color":"FF4D5B","amount":"20"}]
     * coupon_id : 0
     * coupon_amount : 0
     * coupons : [{"coupon_id":"50","order_amount":"200.00","coupon_amount":"50.00"},{"coupon_id":"49","order_amount":"100.00","coupon_amount":"10.00"}]
     * hongbao_id : 0
     * hongbao_amount : 0
     * hongbaos : [{"hongbao_id":"689","min_amount":"50.00","amount":"15.00"},{"hongbao_id":"693","min_amount":"50.00","amount":"15.00"},{"hongbao_id":"705","min_amount":"50.00","amount":"15.00"},{"hongbao_id":"679","min_amount":"50.00","amount":"15.00"},{"hongbao_id":"692","min_amount":"40.00","amount":"8.00"},{"hongbao_id":"678","min_amount":"40.00","amount":"8.00"},{"hongbao_id":"704","min_amount":"40.00","amount":"8.00"},{"hongbao_id":"688","min_amount":"40.00","amount":"8.00"},{"hongbao_id":"677","min_amount":"30.00","amount":"5.00"},{"hongbao_id":"687","min_amount":"30.00","amount":"5.00"},{"hongbao_id":"691","min_amount":"30.00","amount":"5.00"},{"hongbao_id":"703","min_amount":"30.00","amount":"5.00"},{"hongbao_id":"702","min_amount":"20.00","amount":"3.00"},{"hongbao_id":"686","min_amount":"20.00","amount":"3.00"},{"hongbao_id":"676","min_amount":"20.00","amount":"3.00"},{"hongbao_id":"690","min_amount":"20.00","amount":"3.00"}]
     * product_lists : [{"title":"测试商品2","spec_id":"0","spec_name":"","num":"31","price":"8.00"}]
     * shop_id : 1
     * shop_title : 卡旺卡
     * shop_addr : 五彩国际
     * shop_lng : 117.257184
     * shop_lat : 31.835824
     * m_addr : {"addr_id":"91","uid":"59","contact":"kjhjlkjl","mobile":"13899990000","addr":"华润五彩城","house":"00","lat":"31.835828","dateline":"1497246123","lng":"117.257186","type":"1","is_default":"0"}
     * mymoney : 9999782.00
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
    public String coupon_id;
    public String coupon_amount;
    public String hongbao_id;
    public String hongbao_amount;
    public String shop_id;
    public String shop_title;
    public String shop_addr;
    public String shop_lng;
    public String shop_lat;
    public MAddrEntity m_addr;
    public String mymoney;
    public List<Data_WaiMai_ConfirmOrder.YouhuiEntity> youhui;
    public List<CouponsEntity> coupons;
    public List<Data_WaiMai_ConfirmOrder.HongbaosEntity> hongbaos;
    public List<Data_WaiMai_ConfirmOrder.ProductListsEntity> product_lists;

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

    public static class CouponsEntity {
        /**
         * coupon_id : 50
         * order_amount : 200.00
         * coupon_amount : 50.00
         */

        public String coupon_id;
        public String order_amount;
        public String coupon_amount;
    }

    public static class HongbaosEntity {
        /**
         * hongbao_id : 689
         * min_amount : 50.00
         * amount : 15.00
         */

        public int hongbao_id;
        public String min_amount;
        public String amount;
    }

}
