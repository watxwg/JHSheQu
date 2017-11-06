package com.jhcms.mall.model;

import java.util.List;

/**
 * Created by wangyujie
 * on 2017/9/13.17:27
 * TODO 商城订单列表
 */

public class MallOrderItemMode {

    /**
     * count : {"need_pay":"0","need_fahuo":"25","need_shouhuo":"3","need_comment":"3","refund":"5"}
     * items : [{"order_id":"3486","shop_id":"7","order_status":"1","pay_status":"1","total_price":"160.00","money":"0.00","amount":"180.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"待发货","msg_detail":"等待卖家发货","show_btn":[],"class":"ico2"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3486.html","freight":"20.00","product_number":"2","shop_title":"杂货铺","products":[{"pid":"271","product_id":"13","product_name":"情侣T恤","product_price":"80.00","product_number":"2","amount":"160.00","stock_name":"85_88","stock_real_name":"白色/S","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201705/20170524_81EE8DF090E93050DC61C68D714264D3.jpg"}]},{"order_id":"3485","shop_id":"1","order_status":"1","pay_status":"1","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"待发货","msg_detail":"等待卖家发货","show_btn":[],"class":"ico2"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3485.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"270","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3484","shop_id":"1","order_status":"-1","pay_status":"0","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"交易关闭","msg_detail":"该订单已关闭","show_btn":[],"class":"ico5"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3484.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"269","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3483","shop_id":"1","order_status":"-1","pay_status":"0","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"交易关闭","msg_detail":"该订单已关闭","show_btn":[],"class":"ico5"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3483.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"268","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3482","shop_id":"1","order_status":"-1","pay_status":"0","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"交易关闭","msg_detail":"该订单已关闭","show_btn":[],"class":"ico5"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3482.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"267","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3481","shop_id":"1","order_status":"1","pay_status":"1","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"待发货","msg_detail":"等待卖家发货","show_btn":[],"class":"ico2"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3481.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"266","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3480","shop_id":"1","order_status":"1","pay_status":"1","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"待发货","msg_detail":"等待卖家发货","show_btn":[],"class":"ico2"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3480.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"265","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3479","shop_id":"1","order_status":"1","pay_status":"1","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"待发货","msg_detail":"等待卖家发货","show_btn":[],"class":"ico2"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3479.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"264","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3478","shop_id":"1","order_status":"-1","pay_status":"0","total_price":"99.00","money":"0.00","amount":"99.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"交易关闭","msg_detail":"该订单已关闭","show_btn":[],"class":"ico5"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3478.html","freight":"0.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"263","product_id":"27","product_name":"产自青藏高原的无污染液化空气","product_price":"99.00","product_number":"1","amount":"99.00","stock_name":"110","stock_real_name":"小瓶装","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201706/20170613_212EF2D8C7881BEEE81CD7A995F396B5.jpg"}]},{"order_id":"3345","shop_id":"1","order_status":"1","pay_status":"1","total_price":"188.00","money":"0.00","amount":"189.00","coupon":"0.00","change_price":"0.00","msg":{"showmsg":"待发货","msg_detail":"等待卖家发货","show_btn":[],"class":"ico2"},"detail":"http://mall.o2o.jhcms.cn/ucenter/order/detail-3345.html","freight":"1.00","product_number":"1","shop_title":"卡旺卡(商城)","products":[{"pid":"202","product_id":"21","product_name":"美女一枚","product_price":"188.00","product_number":"1","amount":"188.00","stock_name":"48_51_54","stock_real_name":"30/30/30/","tuikuan_status":"0","tuikuan_id":"0","photo":"0"}]}]
     */

    public CountBean count;
    public List<ItemsBean> items;

    public static class CountBean {
        /**
         * need_pay : 0
         * need_fahuo : 25
         * need_shouhuo : 3
         * need_comment : 3
         * refund : 5
         */

        public String need_pay;
        public String need_fahuo;
        public String need_shouhuo;
        public String need_comment;
        public String refund;
    }

    public static class ItemsBean {
        /**
         * order_id : 3486
         * shop_id : 7
         * order_status : 1
         * pay_status : 1
         * total_price : 160.00
         * money : 0.00
         * amount : 180.00
         * coupon : 0.00
         * change_price : 0.00
         * msg : {"showmsg":"待发货","msg_detail":"等待卖家发货","show_btn":[],"class":"ico2"}
         * detail : http://mall.o2o.jhcms.cn/ucenter/order/detail-3486.html
         * freight : 20.00
         * product_number : 2
         * shop_title : 杂货铺
         * products : [{"pid":"271","product_id":"13","product_name":"情侣T恤","product_price":"80.00","product_number":"2","amount":"160.00","stock_name":"85_88","stock_real_name":"白色/S","tuikuan_status":"0","tuikuan_id":"0","photo":"photo/201705/20170524_81EE8DF090E93050DC61C68D714264D3.jpg"}]
         */

        public String order_id;
        public String shop_id;
        public String order_status;
        public String pay_status;
        public String total_price;
        public String money;
        public double amount;
        public String coupon;
        public String change_price;
        public MsgBean msg;
        public String detail;
        public double freight;
        public String product_number;
        public String shop_title;
        public List<ProductsBean> products;

        public static class MsgBean {
            /**
             * showmsg : 待发货
             * msg_detail : 等待卖家发货
             * show_btn : []
             * class : ico2
             */

            public String showmsg;
            public String msg_detail;
            public String classX;
            public ShowBtnBean show_btn;
            public ShowurlBean show_url;

            public static class ShowBtnBean {

                public String wuliu;
                public String confirm;
                public String comment;
                public String cancel;
                public String pay;
            }

            public static class ShowurlBean {
                public String wuliu;
                public String detail;
                public String comment;
            }
        }

        public static class ProductsBean {
            /**
             * pid : 271
             * product_id : 13
             * product_name : 情侣T恤
             * product_price : 80.00
             * product_number : 2
             * amount : 160.00
             * stock_name : 85_88
             * stock_real_name : 白色/S
             * tuikuan_status : 0
             * tuikuan_id : 0
             * photo : photo/201705/20170524_81EE8DF090E93050DC61C68D714264D3.jpg
             */

            public String pid;
            public String product_id;
            public String product_name;
            public String product_price;
            public String product_number;
            public String amount;
            public String stock_name;
            public String stock_real_name;
            public String tuikuan_status;
            public String tuikuan_id;
            public String photo;
        }
    }
}
