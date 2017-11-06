
package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;


public class Data_WaiMai_Home implements Serializable {
    /*外卖首页广告*/
    public List<Adv> adv;
    /*社区首页广告*/
    public List<Adv> advs;
    /*外卖首页广告位*/
    public List<Banner> banner;
    /*社区首页广告位*/
    public List<Banner> banners;
    /*社区、外卖首页分类*/
    public List<IndexCate> index_cate;
    public List<IndexCate> cate_adv;
    /*外卖首页 商铺列表*/
    public List<ShopItems> shop_items;
    /*社区首页 商铺列表*/
    public List<ShopItems> items;
    public List<HuoDong> huodong;
    public List<ShopLikes> likes;
    public ShopDetail detail;
    public ShopHongBao hongbao;
    /*头条下方图片广告位*/
    public AdvButton adv_buttom;
    public List<News> news;
    public String news_more;

    public class AdvButton {
        public String adv_id;
        public String title;
        public String link;
        public String thumb;
    }

    public class News {
        public String article_id;
        public String title;
        public String link;
    }

    public class HuoDong {

        /**
         * title : 商城购物
         * more_url : http://o2o.jhcms.cn/huodong/actlist-2.html
         * items : [{"active_id":"47","thumb":"photo/201705/20170515_FF1EFA165809888F98B95F77E05C95E9.png","link":"http://o2o.jhcms.cn/huodong/detail-47.html"}]
         */

        public String title;
        public String more_url;
        public List<ItemsBean> items;

        public class ItemsBean {
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
}
