package com.jhcms.mall.model;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by wangyujie
 * on 2017/9/13.17:27
 * TODO 商城订单列表
 */

public class MallAddrAllAreas  {


    public List<ItemsBean> items;


    public static class ItemsBean implements IPickerViewData{
        /**
         * region_id : 1
         * region_name : 北京
         * parent_id : 0
         * path_ids : ,1,
         * level : 1
         * map_x :
         * map_y :
         * orderby : 50
         * closed : 0
         * city_code : 0
         * city : [{"region_id":"2","region_name":"北京市","parent_id":"1","path_ids":",1,2,","level":"2","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0","area":[{"region_id":"3","region_name":"东城区","parent_id":"2","path_ids":",1,2,3,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"4","region_name":"西城区","parent_id":"2","path_ids":",1,2,4,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"5","region_name":"崇文区","parent_id":"2","path_ids":",1,2,5,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"6","region_name":"宣武区","parent_id":"2","path_ids":",1,2,6,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"7","region_name":"朝阳区","parent_id":"2","path_ids":",1,2,7,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"8","region_name":"丰台区","parent_id":"2","path_ids":",1,2,8,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"9","region_name":"石景山区","parent_id":"2","path_ids":",1,2,9,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"10","region_name":"海淀区","parent_id":"2","path_ids":",1,2,10,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"11","region_name":"门头沟区","parent_id":"2","path_ids":",1,2,11,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"12","region_name":"房山区","parent_id":"2","path_ids":",1,2,12,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"13","region_name":"通州区","parent_id":"2","path_ids":",1,2,13,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"14","region_name":"顺义区","parent_id":"2","path_ids":",1,2,14,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"15","region_name":"昌平区","parent_id":"2","path_ids":",1,2,15,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"16","region_name":"大兴区","parent_id":"2","path_ids":",1,2,16,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"17","region_name":"怀柔区","parent_id":"2","path_ids":",1,2,17,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"18","region_name":"平谷区","parent_id":"2","path_ids":",1,2,18,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"19","region_name":"密云县","parent_id":"2","path_ids":",1,2,19,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"20","region_name":"延庆县","parent_id":"2","path_ids":",1,2,20,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"}]}]
         */

        public String region_id;
        public String region_name;
        public String parent_id;
        public String path_ids;
        public String level;
        public String map_x;
        public String map_y;
        public String orderby;
        public String closed;
        public String city_code;
        public List<CityBean> city;

        @Override
        public String getPickerViewText() {
            return region_name;
        }

        public static class CityBean {
            /**
             * region_id : 2
             * region_name : 北京市
             * parent_id : 1
             * path_ids : ,1,2,
             * level : 2
             * map_x :
             * map_y :
             * orderby : 50
             * closed : 0
             * city_code : 0
             * area : [{"region_id":"3","region_name":"东城区","parent_id":"2","path_ids":",1,2,3,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"4","region_name":"西城区","parent_id":"2","path_ids":",1,2,4,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"5","region_name":"崇文区","parent_id":"2","path_ids":",1,2,5,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"6","region_name":"宣武区","parent_id":"2","path_ids":",1,2,6,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"7","region_name":"朝阳区","parent_id":"2","path_ids":",1,2,7,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"8","region_name":"丰台区","parent_id":"2","path_ids":",1,2,8,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"9","region_name":"石景山区","parent_id":"2","path_ids":",1,2,9,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"10","region_name":"海淀区","parent_id":"2","path_ids":",1,2,10,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"11","region_name":"门头沟区","parent_id":"2","path_ids":",1,2,11,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"12","region_name":"房山区","parent_id":"2","path_ids":",1,2,12,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"13","region_name":"通州区","parent_id":"2","path_ids":",1,2,13,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"14","region_name":"顺义区","parent_id":"2","path_ids":",1,2,14,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"15","region_name":"昌平区","parent_id":"2","path_ids":",1,2,15,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"16","region_name":"大兴区","parent_id":"2","path_ids":",1,2,16,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"17","region_name":"怀柔区","parent_id":"2","path_ids":",1,2,17,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"18","region_name":"平谷区","parent_id":"2","path_ids":",1,2,18,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"19","region_name":"密云县","parent_id":"2","path_ids":",1,2,19,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"},{"region_id":"20","region_name":"延庆县","parent_id":"2","path_ids":",1,2,20,","level":"3","map_x":"","map_y":"","orderby":"50","closed":"0","city_code":"0"}]
             */

            public String region_id;
            public String region_name;
            public String parent_id;
            public String path_ids;
            public String level;
            public String map_x;
            public String map_y;
            public String orderby;
            public String closed;
            public String city_code;
            public List<AreaBean> area;

            public static class AreaBean {
                /**
                 * region_id : 3
                 * region_name : 东城区
                 * parent_id : 2
                 * path_ids : ,1,2,3,
                 * level : 3
                 * map_x :
                 * map_y :
                 * orderby : 50
                 * closed : 0
                 * city_code : 0
                 */

                public String region_id;
                public String region_name;
                public String parent_id;
                public String path_ids;
                public String level;
                public String map_x;
                public String map_y;
                public String orderby;
                public String closed;
                public String city_code;
            }
        }
    }
}
