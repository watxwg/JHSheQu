package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/14.
 * TODO 订单详情
 */

public class Data_WaiMai_OrderDetail {

    /**
     * detail : {"order_id":"551","staff_id":"0","uid":"59","order_status":"0","pay_status":"1","total_price":"13.00","money":"0.00","amount":"13.00","contact":"kjhjlkjl","mobile":"13899990000","addr":"华润五彩城","house":"00","lng":"117.257186","lat":"31.835828","intro":"","order_from":"android","pay_code":"money","pay_time":"1497430544","pei_time":"0","pei_type":"0","dateline":"1497430210","comment_status":"0","cui_time":"0","product_price":"8.00","package_price":"2.00","freight":"3.00","spend_number":"","spend_status":"0","msg":"等待商户接单","order_status_label":"待接单","refund":"-1","jifen_total":"13","staff":{"staff_id":"0"},"waimai":{"shop_id":"1","title":"卡旺卡","phone":"13901010101","lat":"31.835824","lng":"117.257184","logo":"photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg","addr":"五彩国际"},"products":[{"order_id":"551","product_id":"2","product_name":"测试商品2","product_price":"8.00","package_price":"2.00","product_number":"1","amount":"10.00"}],"kefu_phone":"0551-64278115","payment_type":"现金支付","complaint":"0","youhui":[],"pay_ltime":"15","hongbao":{"link":"http://waimai.o2o.jhcms.cn/hongbao/index-551.html","title":"端午节江湖外卖送你大红包啦！！！","desc":"大额满减，低值1折，等你来吃。赶快领取吧！5","imgUrl":"/attachs/"},"time":[{"minute":"10","date":"08:10"},{"minute":"20","date":"08:20"},{"minute":"30","date":"08:30"},{"minute":"40","date":"08:40"},{"minute":"50","date":"08:50"},{"minute":"60","date":"09:00"},{"minute":"70","date":"09:10"},{"minute":"80","date":"09:20"},{"minute":"90","date":"09:30"},{"minute":"100","date":"09:40"},{"minute":"110","date":"09:50"},{"minute":"120","date":"10:00"}]}
     */

    public DetailEntity detail;

    public static class DetailEntity {
        /**
         * order_id : 551
         * staff_id : 0
         * uid : 59
         * order_status : 0
         * pay_status : 1
         * total_price : 13.00
         * money : 0.00
         * amount : 13.00
         * contact : kjhjlkjl
         * mobile : 13899990000
         * addr : 华润五彩城
         * house : 00
         * lng : 117.257186
         * lat : 31.835828
         * intro :
         * order_from : android
         * pay_code : money
         * pay_time : 1497430544
         * pei_time : 0
         * pei_type : 0
         * dateline : 1497430210
         * comment_status : 0
         * cui_time : 0
         * product_price : 8.00
         * package_price : 2.00
         * freight : 3.00
         * spend_number :
         * spend_status : 0
         * msg : 等待商户接单
         * order_status_label : 待接单
         * refund : -1
         * jifen_total : 13
         * staff : {"staff_id":"0"}
         * waimai : {"shop_id":"1","title":"卡旺卡","phone":"13901010101","lat":"31.835824","lng":"117.257184","logo":"photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg","addr":"五彩国际"}
         * products : [{"order_id":"551","product_id":"2","product_name":"测试商品2","product_price":"8.00","package_price":"2.00","product_number":"1","amount":"10.00"}]
         * kefu_phone : 0551-64278115
         * payment_type : 现金支付
         * complaint : 0
         * youhui : []
         * pay_ltime : 15
         * hongbao : {"link":"http://waimai.o2o.jhcms.cn/hongbao/index-551.html","title":"端午节江湖外卖送你大红包啦！！！","desc":"大额满减，低值1折，等你来吃。赶快领取吧！5","imgUrl":"/attachs/"}
         * time : [{"minute":"10","date":"08:10"},{"minute":"20","date":"08:20"},{"minute":"30","date":"08:30"},{"minute":"40","date":"08:40"},{"minute":"50","date":"08:50"},{"minute":"60","date":"09:00"},{"minute":"70","date":"09:10"},{"minute":"80","date":"09:20"},{"minute":"90","date":"09:30"},{"minute":"100","date":"09:40"},{"minute":"110","date":"09:50"},{"minute":"120","date":"10:00"}]
         */

        public String order_id;
        public String staff_id;
        public String uid;
        public String order_status;
        public String pay_status;
        public String total_price;
        public String money;
        public String amount;
        public String contact;
        public String mobile;
        public String addr;
        public String house;
        public String lng;
        public String lat;
        public String intro;
        public String order_from;
        public String pay_code;
        public String pay_time;
        public String pei_time;
        public String pei_type;
        public String dateline;
        public String comment_status;
        public String cui_time;
        public String product_price;
        public String package_price;
        public String freight;
        public String spend_number;
        public String spend_status;
        public String msg;
        public String order_status_label;
        public String refund;
        public String jifen_total;
        public StaffEntity staff;
        public WaimaiEntity waimai;
        public String kefu_phone;
        public String payment_type;
        public String complaint;
        public String pay_ltime;
        public HongbaoEntity hongbao;
        public List<ProductsEntity> products;
        public List<YouhuiEntity> youhui;
        public List<TimeEntity> time;

        public static class StaffEntity {
            /**
             * staff_id : 0
             */

            public String staff_id;
            public String mobile;
            public String name;
            public String face;
            public double lat;
            public double lng;
        }

        public static class WaimaiEntity {
            /**
             * shop_id : 1
             * title : 卡旺卡
             * phone : 13901010101
             * lat : 31.835824
             * lng : 117.257184
             * logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
             * addr : 五彩国际
             */

            public String shop_id;
            public String title;
            public String phone;
            public String lat;
            public String lng;
            public String logo;
            public String addr;
        }

        public static class HongbaoEntity {
            /**
             * link : http://waimai.o2o.jhcms.cn/hongbao/index-551.html
             * title : 端午节江湖外卖送你大红包啦！！！
             * desc : 大额满减，低值1折，等你来吃。赶快领取吧！5
             * imgUrl : /attachs/
             */

            public String link;
            public String title;
            public String desc;
            public String imgUrl;
        }

        public static class ProductsEntity {
            /**
             * order_id : 551
             * product_id : 2
             * product_name : 测试商品2
             * product_price : 8.00
             * package_price : 2.00
             * product_number : 1
             * amount : 10.00
             */

            public String order_id;
            public String product_id;
            public String product_name;
            public String product_price;
            public String package_price;
            public String product_number;
            public String amount;
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
            public float amount;

        }

        public static class TimeEntity {
            /**
             * minute : 10
             * date : 08:10
             */

            public String minute;
            public String date;
        }
    }
}
