package com.jhcms.mall.model;

/**
 * Created by wangyujie
 * on 2017/9/13.17:27
 * TODO 商城个人中心
 */

public class MallMemberInfoMode {

    /**
     * detail : {"uid":"4","mobile":"17756570802","nickname":"张四海","sex":"1","money":"141499.70","orders":"117","jifen":"5904","face":"photo/201707/20170726_347C0B763B155A3A3051C117A02C57AE.png","wx_openid":"oju9_wjRLDJhnfO7BDPbG7bVut38","wx_unionid":"oGR4SuFLofImgX0_CoGL2uDVME0s","loginip":"112.26.23.195","lastlogin":"1503737051","order_count":{"need_pay":"0","need_fahuo":"12","need_shouhuo":"1","need_comment":"7","refund":"19"},"collect_goods_url":"http://mall.o2o.jhcms.cn/ucenter/collect/goods.html","collect_shops_url":"http://mall.o2o.jhcms.cn/ucenter/collect/shops.html","coupon_url":"http://mall.o2o.jhcms.cn/ucenter/coupon/index.html","msg_new_count":"311"}
     */

    public DetailBean detail;

    public static class DetailBean {
        /**
         * uid : 4
         * mobile : 17756570802
         * nickname : 张四海
         * sex : 1
         * money : 141499.70
         * orders : 117
         * jifen : 5904
         * face : photo/201707/20170726_347C0B763B155A3A3051C117A02C57AE.png
         * wx_openid : oju9_wjRLDJhnfO7BDPbG7bVut38
         * wx_unionid : oGR4SuFLofImgX0_CoGL2uDVME0s
         * loginip : 112.26.23.195
         * lastlogin : 1503737051
         * order_count : {"need_pay":"0","need_fahuo":"12","need_shouhuo":"1","need_comment":"7","refund":"19"}
         * collect_goods_url : http://mall.o2o.jhcms.cn/ucenter/collect/goods.html
         * collect_shops_url : http://mall.o2o.jhcms.cn/ucenter/collect/shops.html
         * coupon_url : http://mall.o2o.jhcms.cn/ucenter/coupon/index.html
         * msg_new_count : 311
         */

        public String uid;
        public String mobile;
        public String nickname;
        public String sex;
        public String money;
        public String orders;
        public String jifen;
        public String face;
        public String wx_openid;
        public String wx_unionid;
        public String loginip;
        public String lastlogin;
        public OrderCountBean order_count;
        public String collect_goods_url;
        public String collect_shops_url;
        public String coupon_url;
        public int msg_new_count;

        public static class OrderCountBean {
            /**
             * need_pay : 0
             * need_fahuo : 12
             * need_shouhuo : 1
             * need_comment : 7
             * refund : 19
             */

            public int need_pay;
            public int need_fahuo;
            public int need_shouhuo;
            public int need_comment;
            public int refund;
        }
    }
}
