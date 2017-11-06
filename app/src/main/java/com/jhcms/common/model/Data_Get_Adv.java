package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/7/1.
 * TODO 广告
 */

public class Data_Get_Adv {

    /**
     * items : [{"adv_id":"27","title":"1111","link":"http://www.google.com","thumb":"photo/201702/20170215_9D6182B84863946166DE6AF53F79A5A3.jpg","ltime":"1593014400","stime":"1485619200"}]
     */

    public List<ItemsEntity> items;

    public static class ItemsEntity {
        /**
         * adv_id : 27
         * title : 1111
         * link : http://www.google.com
         * thumb : photo/201702/20170215_9D6182B84863946166DE6AF53F79A5A3.jpg
         * ltime : 1593014400
         * stime : 1485619200
         */

        public String adv_id;
        public String title;
        public String link;
        public String thumb;
        public String ltime;
        public String stime;
    }
}
