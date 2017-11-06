package com.jhcms.mall.model;

/**
 * Created by wangyujie
 * on 2017/9/13.17:27
 * TODO 商城订单列表
 */

public class MallAddrDetail {

    /**
     * detail : {"addr_id":"20","province_name":"安徽","city_name":"合肥","area_name":"蜀山区","contact":"蛇皮han涛","addr":"华润五彩国际905","mobile":"13999994444","is_default":"0","province_id":"104","city_id":"105","area_id":"108"}
     */

    public DetailBean detail;

    public static class DetailBean {
        /**
         * addr_id : 20
         * province_name : 安徽
         * city_name : 合肥
         * area_name : 蜀山区
         * contact : 蛇皮han涛
         * addr : 华润五彩国际905
         * mobile : 13999994444
         * is_default : 0
         * province_id : 104
         * city_id : 105
         * area_id : 108
         */

        public String addr_id;
        public String province_name;
        public String city_name;
        public String area_name;
        public String contact;
        public String addr;
        public String mobile;
        public String is_default;
        public String province_id;
        public String city_id;
        public String area_id;
    }
}
