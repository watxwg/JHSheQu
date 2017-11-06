package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/13.
 * TODO
 */

public class Data_WaiMai_ShopDetail {


    /**
     * detail : {"product_id":"3","shop_id":"1","title":"测试商品3","photo":"photo/201705/20170513_99E4DB05CF0B70A21EEE86A6A07EDB0A.jpg","price":"15.00","package_price":"2.00","sales":"30","sale_type":"1","sale_sku":"2031","sale_count":"30","intro":"","is_spec":"1","good":"0","bad":"1","good_rate":"0","specs":[{"spec_id":"30","product_id":"3","price":"15.00","package_price":"2.00","spec_name":"大份","spec_photo":"photo/201705/20170513_BDDF380165746B18C7F5C2AB2D6491AE.jpg","sale_sku":"1009","sale_count":"2"},{"spec_id":"31","product_id":"3","price":"10.00","package_price":"2.00","spec_name":"小份","spec_photo":"photo/201705/20170513_DB18B08404C2B480AB9684886783B351.jpg","sale_sku":"1006","sale_count":"5"}],"shop_title":"卡旺卡","phone":"13901010101"}
     */

    public DetailEntity detail;

    public static class DetailEntity {
        /**
         * product_id : 3
         * shop_id : 1
         * title : 测试商品3
         * photo : photo/201705/20170513_99E4DB05CF0B70A21EEE86A6A07EDB0A.jpg
         * price : 15.00
         * package_price : 2.00
         * sales : 30
         * sale_type : 1
         * sale_sku : 2031
         * sale_count : 30
         * intro :
         * is_spec : 1
         * good : 0
         * bad : 1
         * good_rate : 0
         * specs : [{"spec_id":"30","product_id":"3","price":"15.00","package_price":"2.00","spec_name":"大份","spec_photo":"photo/201705/20170513_BDDF380165746B18C7F5C2AB2D6491AE.jpg","sale_sku":"1009","sale_count":"2"},{"spec_id":"31","product_id":"3","price":"10.00","package_price":"2.00","spec_name":"小份","spec_photo":"photo/201705/20170513_DB18B08404C2B480AB9684886783B351.jpg","sale_sku":"1006","sale_count":"5"}]
         * shop_title : 卡旺卡
         * phone : 13901010101
         */

        public String product_id;
        public String shop_id;
        public String title;
        public String photo;
        public String price;
        public String package_price;
        public String sales;
        public String sale_type;
        public int sale_sku;
        public String sale_count;
        public String intro;
        public String is_spec;
        public String good;
        public String bad;
        public String good_rate;
        public String shop_title;
        public String phone;
        public List<SpecsEntity> specs;

        public static class SpecsEntity {
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
            public String sale_sku;
            public String sale_count;
        }
    }
}
