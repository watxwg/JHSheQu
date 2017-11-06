package com.jhcms.common.model;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */

public class Data_Group_Shop_Items {

    public List<ItemsBean> items;

    public static class ItemsBean {
        /**
         * shop_id : 1
         * title : 卡旺卡
         * have_tuan : 1
         * have_maidan : 1
         * lat : 31.835824
         * lng : 117.257184
         * logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
         * score : 132
         * comments : 34
         * addr : 合肥市蜀山区华润五彩城国际
         * cate_name : 美食-快餐便当
         * juli : 109.04
         * juli_label : 109m
         * avg_score : 3.9
         * products : [{"tuan_id":"1","title":"方燕烤猪蹄","price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","sales":"23"},{"tuan_id":"2","title":"重庆·猪圈火锅","price":"89.00","market_price":"100.00","photo":"photo/201707/20170714_DF95D1209F67E821F09743CF97E01A07.jpg","sales":"18"}]
         * total_count : 3
         * other_count : 1
         */

        public String shop_id;
        public String title;
        public String have_tuan;
        public String have_maidan;
        public String lat;
        public String lng;
        public String logo;
        public String score;
        public String comments;
        public String addr;
        public String cate_name;
        public String juli;
        public String juli_label;
        public String avg_score;
        public int total_count;
        public String other_count;
        public List<Data_Group_Home.ShopItemsEntity.ProductsEntity> products;
        public String avg_amount;
    }
}
