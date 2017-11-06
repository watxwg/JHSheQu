package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin
 * on 2017/8/4.
 * TODO
 */

public class Data_Group_Shop_Album implements Serializable {

    public List<ItemsBean> items;

    public static class ItemsBean implements Serializable{
        /**
         * photo_id : 9
         * shop_id : 1
         * title : 商品
         * album_id : 0
         * photo : photo/201707/20170718_7E3C55558539B82310BF8AB64A6D598B.jpg
         * dateline : 1500372791
         * type : 1
         */

        public String photo_id;
        public String shop_id;
        public String title;
        public String album_id;
        public String photo;
        public String dateline;
        public String type;
    }
}
