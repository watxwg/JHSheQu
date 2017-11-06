package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/8.
 * TODO 外卖商户详情
 */

public class ShopDetail implements Serializable {


    /**
     * shop_id : 1
     * cate_id : 16
     * title : 卡旺卡
     * lat : 31.835824
     * lng : 117.257184
     * logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
     * orders : 131
     * min_amount : 0.00
     * freight : 0.00
     * pei_type : 0
     * pei_time : 26
     * yy_status : 1
     * delcare : 欢迎光临
     * yysj_status : 1
     * huodong : [{"title":"首单立减10元","word":"首","color":"20AD20"},{"title":"满20减2,满50减8,满100减20","word":"减","color":"FF4D5B"},{"title":"进店领160元优惠券","word":"券","color":"AA4CFF"},{"title":"实付满100元返2元优惠券,实付满200元返3元优惠券","word":"返","color":"FF9900"}]
     * collect : 0
     * avg_score : 4.2
     * share_url : http://waimai.o2o.jhcms.cn/shop/detail-1.html
     * shop_coupon : {"title":"共3张","coupon_amount":"160"}
     * items : [{"title":"热销","cate_id":"hot","shop_id":"1","products":[{"product_id":"1","shop_id":"1","cate_id":"1","title":"测试商品1","photo":"photo/201705/20170513_09C3EF8D8E86EF3DE7CB787997F30841.jpg","price":"9.00","package_price":"2.00","sales":"166","sale_type":"1","sale_sku":"2","is_spec":"0","good":"11","bad":"0"},{"product_id":"2","shop_id":"1","cate_id":"1","title":"测试商品2","photo":"photo/201705/20170513_A6D9B28797FF1DDF12D70B3F586ECF3F.jpg","price":"8.00","package_price":"2.00","sales":"161","sale_type":"1","sale_sku":"897","is_spec":"0","good":"3","bad":"2"},{"product_id":"10","shop_id":"1","cate_id":"1","title":"测试商品4","photo":"photo/201705/20170513_22A45D43117C7098994F529439608D6C.jpg","price":"0.01","package_price":"0.00","sales":"35","sale_type":"1","sale_sku":"10086","is_spec":"0","good":"1","bad":"0"},{"product_id":"3","shop_id":"1","cate_id":"2","title":"测试商品3","photo":"photo/201705/20170513_99E4DB05CF0B70A21EEE86A6A07EDB0A.jpg","price":"15.00","package_price":"2.00","sales":"30","sale_type":"1","sale_sku":"2031","is_spec":"1","good":"0","bad":"1","specs":[{"spec_id":"30","product_id":"3","price":"15.00","package_price":"2.00","spec_name":"大份","spec_photo":"photo/201705/20170513_BDDF380165746B18C7F5C2AB2D6491AE.jpg","sale_sku":"1009","sale_count":"2"},{"spec_id":"31","product_id":"3","price":"10.00","package_price":"2.00","spec_name":"小份","spec_photo":"photo/201705/20170513_DB18B08404C2B480AB9684886783B351.jpg","sale_sku":"1006","sale_count":"5"}]},{"product_id":"39","shop_id":"1","cate_id":"1","title":"烤鱼","photo":"photo/201705/20170524_55A60324756154AA4C40A750E52EC3E1.jpg","price":"1.00","package_price":"1.00","sales":"3","sale_type":"1","sale_sku":"996","is_spec":"0","good":"0","bad":"0"}]},{"title":"分类1","cate_id":"1","shop_id":"1","products":[{"product_id":"1","shop_id":"1","cate_id":"1","title":"测试商品1","photo":"photo/201705/20170513_09C3EF8D8E86EF3DE7CB787997F30841.jpg","price":"9.00","package_price":"2.00","sales":"166","sale_type":"1","sale_sku":"2","is_spec":"0","good":"11","bad":"0"},{"product_id":"2","shop_id":"1","cate_id":"1","title":"测试商品2","photo":"photo/201705/20170513_A6D9B28797FF1DDF12D70B3F586ECF3F.jpg","price":"8.00","package_price":"2.00","sales":"161","sale_type":"1","sale_sku":"897","is_spec":"0","good":"3","bad":"2"},{"product_id":"10","shop_id":"1","cate_id":"1","title":"测试商品4","photo":"photo/201705/20170513_22A45D43117C7098994F529439608D6C.jpg","price":"0.01","package_price":"0.00","sales":"35","sale_type":"1","sale_sku":"10086","is_spec":"0","good":"1","bad":"0"},{"product_id":"39","shop_id":"1","cate_id":"1","title":"烤鱼","photo":"photo/201705/20170524_55A60324756154AA4C40A750E52EC3E1.jpg","price":"1.00","package_price":"1.00","sales":"3","sale_type":"1","sale_sku":"996","is_spec":"0","good":"0","bad":"0"}]},{"title":"分类2","cate_id":"2","shop_id":"1","products":[{"product_id":"3","shop_id":"1","cate_id":"2","title":"测试商品3","photo":"photo/201705/20170513_99E4DB05CF0B70A21EEE86A6A07EDB0A.jpg","price":"15.00","package_price":"2.00","sales":"30","sale_type":"1","sale_sku":"2031","is_spec":"1","good":"0","bad":"1","specs":[{"spec_id":"30","product_id":"3","price":"15.00","package_price":"2.00","spec_name":"大份","spec_photo":"photo/201705/20170513_BDDF380165746B18C7F5C2AB2D6491AE.jpg","sale_sku":"1009","sale_count":"2"},{"spec_id":"31","product_id":"3","price":"10.00","package_price":"2.00","spec_name":"小份","spec_photo":"photo/201705/20170513_DB18B08404C2B480AB9684886783B351.jpg","sale_sku":"1006","sale_count":"5"}]}]}]
     */

    public String shop_id;
    public String cate_id;
    public String title;
    public String lat;
    public String lng;
    public String logo;
    public String orders;
    public String min_amount;
    public String freight;
    public String pei_type;
    public String pei_time;
    public String yy_status;
    public String delcare;
    public String yysj_status;
    public String collect;
    public String avg_score;
    public String share_url;
    public ShopCouponEntity shop_coupon;
    public List<HuodongEntity> huodong;
    public List<ItemsEntity> items;


    public static class ShopCouponEntity implements Serializable {
        /**
         * title : 共3张
         * coupon_amount : 160
         */

        public String title;
        public String coupon_amount;
        public String link;

    }

    public static class HuodongEntity implements Serializable {
        /**
         * title : 首单立减10元
         * word : 首
         * color : 20AD20
         */

        public String title;
        public String word;
        public String color;

    }

    public static class ItemsEntity implements Serializable {
        /**
         * title : 热销
         * cate_id : hot
         * shop_id : 1
         * products : [{"product_id":"1","shop_id":"1","cate_id":"1","title":"测试商品1","photo":"photo/201705/20170513_09C3EF8D8E86EF3DE7CB787997F30841.jpg","price":"9.00","package_price":"2.00","sales":"166","sale_type":"1","sale_sku":"2","is_spec":"0","good":"11","bad":"0"},{"product_id":"2","shop_id":"1","cate_id":"1","title":"测试商品2","photo":"photo/201705/20170513_A6D9B28797FF1DDF12D70B3F586ECF3F.jpg","price":"8.00","package_price":"2.00","sales":"161","sale_type":"1","sale_sku":"897","is_spec":"0","good":"3","bad":"2"},{"product_id":"10","shop_id":"1","cate_id":"1","title":"测试商品4","photo":"photo/201705/20170513_22A45D43117C7098994F529439608D6C.jpg","price":"0.01","package_price":"0.00","sales":"35","sale_type":"1","sale_sku":"10086","is_spec":"0","good":"1","bad":"0"},{"product_id":"3","shop_id":"1","cate_id":"2","title":"测试商品3","photo":"photo/201705/20170513_99E4DB05CF0B70A21EEE86A6A07EDB0A.jpg","price":"15.00","package_price":"2.00","sales":"30","sale_type":"1","sale_sku":"2031","is_spec":"1","good":"0","bad":"1","specs":[{"spec_id":"30","product_id":"3","price":"15.00","package_price":"2.00","spec_name":"大份","spec_photo":"photo/201705/20170513_BDDF380165746B18C7F5C2AB2D6491AE.jpg","sale_sku":"1009","sale_count":"2"},{"spec_id":"31","product_id":"3","price":"10.00","package_price":"2.00","spec_name":"小份","spec_photo":"photo/201705/20170513_DB18B08404C2B480AB9684886783B351.jpg","sale_sku":"1006","sale_count":"5"}]},{"product_id":"39","shop_id":"1","cate_id":"1","title":"烤鱼","photo":"photo/201705/20170524_55A60324756154AA4C40A750E52EC3E1.jpg","price":"1.00","package_price":"1.00","sales":"3","sale_type":"1","sale_sku":"996","is_spec":"0","good":"0","bad":"0"}]
         */

        public String title;
        public String cate_id;
        public String shop_id;
        public List<ProductsEntity> products;


        public static class ProductsEntity implements Serializable {
            /**
             * product_id : 1
             * shop_id : 1
             * cate_id : 1
             * title : 测试商品1
             * photo : photo/201705/20170513_09C3EF8D8E86EF3DE7CB787997F30841.jpg
             * price : 9.00
             * package_price : 2.00
             * sales : 166
             * sale_type : 1
             * sale_sku : 2
             * is_spec : 0
             * good : 11
             * bad : 0
             * specs : [{"spec_id":"30","product_id":"3","price":"15.00","package_price":"2.00","spec_name":"大份","spec_photo":"photo/201705/20170513_BDDF380165746B18C7F5C2AB2D6491AE.jpg","sale_sku":"1009","sale_count":"2"},{"spec_id":"31","product_id":"3","price":"10.00","package_price":"2.00","spec_name":"小份","spec_photo":"photo/201705/20170513_DB18B08404C2B480AB9684886783B351.jpg","sale_sku":"1006","sale_count":"5"}]
             */

            public String product_id;
            public String shop_id;
            public String cate_id;
            public String title;
            public String photo;
            public String price;
            public String package_price;
            public String sales;
            public String sale_type;
            public int sale_sku;
            public String is_spec;
            public String good;
            public String bad;
            public List<SpecsEntity> specs;


            public static class SpecsEntity implements Serializable {
                /**
                 * spec_id : 30
                 * product_id : 3
                 * price : 15.00
                 * package_price : 2.00
                 * spec_name : 大份
                 * spec_photo : photo/201705/20170513_BDDF380165746B18C7F5C2AB2D6491AE.jpg
                 * sale_sku : 1009
                 * sale_count : 2
                 */

                public String spec_id;
                public String product_id;
                public String price;
                public String package_price;
                public String spec_name;
                public String spec_photo;
                public int sale_sku;
                public String sale_count;

            }
        }
    }
}
