package com.jhcms.common.model;

import java.util.List;

/**
 * Created by admin
 * on 2017/8/21.
 * TODO
 */

class HuoDong {

    /**
     * title : 商城购物
     * more_url : http://o2o.jhcms.cn/huodong/actlist-2.html
     * items : [{"active_id":"47","thumb":"photo/201705/20170515_FF1EFA165809888F98B95F77E05C95E9.png","link":"http://o2o.jhcms.cn/huodong/detail-47.html"}]
     */

    public String title;
    public String more_url;
    public List<ItemsBean> items;

    public static class ItemsBean {
        /**
         * active_id : 47
         * thumb : photo/201705/20170515_FF1EFA165809888F98B95F77E05C95E9.png
         * link : http://o2o.jhcms.cn/huodong/detail-47.html
         */

        public String active_id;
        public String thumb;
        public String link;
    }
}
