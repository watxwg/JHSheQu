package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/10.
 * TODO 外卖商户评价
 */

public class ShopComment {

    /**
     * comment_id : 10
     * shop_id : 8
     * uid : 1
     * order_id : 214
     * score : 5
     * content : 挺好吃的
     * pei_time : 准时送达
     * have_photo : 1
     * reply : 欢迎下次光临
     * reply_time : 1495518608
     * dateline : 1495518590
     * nickname : 测试买家
     * face : photo/201705/20170509_A6E4FC6DF449AD04ADDC145BF4D0A74C.gif
     * comment_photos : [{"photo":"photo/201705/20170523_3374F85859AB8D4EF46540AFD114AD1E.jpg","photo_id":"18"},{"photo":"photo/201705/20170523_D4B9618C5D53D33A3E5A9702BB4E679A.jpg","photo_id":"19"},{"photo":"photo/201705/20170523_1B520ED8A63E249019DFF03EF565CC04.jpg","photo_id":"20"},{"photo":"photo/201705/20170523_1C07FBFECACC1BD5C02A0235766AFFC1.jpg","photo_id":"21"},{"photo":"photo/201705/20170523_D99BC2B3154D4C83F0493881E3F799D9.jpg","photo_id":"22"}]
     */

    public String comment_id;
    public String shop_id;
    public String uid;
    public String order_id;
    public String score;
    public String content;
    public String pei_time;
    public String have_photo;
    public String reply;
    public String reply_time;
    public String dateline;
    public String nickname;
    public String face;
    public List<CommentPhotosEntity> comment_photos;


    public static class CommentPhotosEntity {
        /**
         * photo : photo/201705/20170523_3374F85859AB8D4EF46540AFD114AD1E.jpg
         * photo_id : 18
         */

        public String photo;
        public String photo_id;

    }
}
