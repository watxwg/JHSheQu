package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/21.
 * TODO 搜索商品
 */

public class Data_WaiMai_SearchGood {

    /**
     * items : [{"product_id":"54","shop_id":"26","title":"店","photo":"photo/201706/20170615_BDBB34904610C73E2BC4BA0FC46F87D5.png","price":"2.00","sales":"4","shop_title":"温州一家人"}]
     * page : 1
     */

    public String page;
    public List<ItemsEntity> items;

    public static class ItemsEntity {
        /**
         * product_id : 54
         * shop_id : 26
         * title : 店
         * photo : photo/201706/20170615_BDBB34904610C73E2BC4BA0FC46F87D5.png
         * price : 2.00
         * sales : 4
         * shop_title : 温州一家人
         */

        public String product_id;
        public String shop_id;
        public String title;
        public String photo;
        public String price;
        public String sales;
        public String shop_title;
    }
}
