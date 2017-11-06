package com.jhcms.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/10.
 */
public class Data_BusinessListSortList {
    public ArrayList<SortModel> items;
    public List<AreaItemsBean> area_items;

    public class AreaItemsBean {
        /**
         * area_id : 1
         * city_id : 1
         * area_name : 蜀山区
         * orderby : 1
         * dateline : 1458877356
         * business : [{"business_id":"0","business_name":"全部"},{"business_id":"1","business_name":"三里庵"},{"business_id":"2","business_name":"国购广场"},{"business_id":"3","business_name":"1912街区"},{"business_id":"4","business_name":"黄金广场"},{"business_id":"5","business_name":"松芝万象城"},{"business_id":"6","business_name":"华润五彩城"},{"business_id":"7","business_name":"步行街"}]
         */

        public int area_id;
        public String city_id;
        public String area_name;
        public String orderby;
        public String dateline;
        public List<BusinessBean> business;

        public class BusinessBean {
            /**
             * business_id : 0
             * business_name : 全部
             */

            public int business_id;
            public String business_name;
        }
    }
}
