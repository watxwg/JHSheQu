package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin
 * on 2017/8/28.
 * TODO
 */

public class Data_Mall_Addr implements Serializable {

    public List<ItemsBean> items;

    public static class ItemsBean {
        /**
         * addr_id : 21
         * province_name : 安徽
         * city_name : 合肥市
         * area_name : 蜀山区
         * contact : jhgdg
         * addr : 发的说法是打发是的发射点
         * mobile : 18756388765
         * is_default : 1
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
