package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/7/19.
 * TODO 团购商家详情
 */

public class Data_Group_Shop_Detail implements Serializable{

    /**
     * detail : {"shop_id":"1","title":"卡旺卡","phone":"13901010101","have_maidan":"1","lat":"31.835824","lng":"117.257184","logo":"photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg","banner":"photo/201707/20170715_8E24E9EB40E70BDB66CF683198DF23D0.jpg","comments":"11","addr":"合肥市蜀山区华润五彩城国际","avg_amount":"20.00","is_wifi":"1","is_cart":"1","avg_score":"3.5","collect":"0","share_info":{"title":"卡旺卡","desc":"卡旺卡团购，优惠多多！","link":"http://o2o.jhcms.cn/tuan/shop/detail-1.html","imgUrl":"photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg"},"waimai":{"word":"外","title":"外卖","color":"FF9900","url":"http://waimai.o2o.jhcms.cn/shop/detail-1.html"},"other_count":"2","tuan":{"word":"团","title":"团购","color":"EE6723","product":[{"tuan_id":"1","title":"方燕烤猪蹄","price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","sales":"9"},{"tuan_id":"2","title":"重庆·猪圈火锅","price":"89.00","market_price":"100.00","photo":"photo/201707/20170714_DF95D1209F67E821F09743CF97E01A07.jpg","sales":"0"}]},"tuijian":[{"tuan_id":"3","title":"胖帅肉蟹煲","price":"86.00","market_price":"100.00","photo":"photo/201707/20170715_E94E73CDF792B21860EBDDF8ECD1A580.jpg","sales":"0"},{"tuan_id":"4","title":"网鱼网咖","price":"19.00","market_price":"50.00","photo":"photo/201707/20170715_317A84DB71D05A7DCB4F62A3E42D56FF.jpg","sales":"1"}],"options":{"shop_id":"1","type":"1","config":[{"m":"50","d":"5"}],"discount":"95","max_youhui":"20","orders":"0"},"photo_count":"2"}
     */

    public DetailBean detail;

    public static class DetailBean implements Serializable{
        /**
         * shop_id : 1
         * title : 卡旺卡
         * phone : 13901010101
         * have_maidan : 1
         * lat : 31.835824
         * lng : 117.257184
         * logo : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
         * banner : photo/201707/20170715_8E24E9EB40E70BDB66CF683198DF23D0.jpg
         * comments : 11
         * addr : 合肥市蜀山区华润五彩城国际
         * avg_amount : 20.00
         * is_wifi : 1
         * is_cart : 1
         * avg_score : 3.5
         * collect : 0
         * share_info : {"title":"卡旺卡","desc":"卡旺卡团购，优惠多多！","link":"http://o2o.jhcms.cn/tuan/shop/detail-1.html","imgUrl":"photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg"}
         * waimai : {"word":"外","title":"外卖","color":"FF9900","url":"http://waimai.o2o.jhcms.cn/shop/detail-1.html"}
         * other_count : 2
         * tuan : {"word":"团","title":"团购","color":"EE6723","product":[{"tuan_id":"1","title":"方燕烤猪蹄","price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","sales":"9"},{"tuan_id":"2","title":"重庆·猪圈火锅","price":"89.00","market_price":"100.00","photo":"photo/201707/20170714_DF95D1209F67E821F09743CF97E01A07.jpg","sales":"0"}]}
         * tuijian : [{"tuan_id":"3","title":"胖帅肉蟹煲","price":"86.00","market_price":"100.00","photo":"photo/201707/20170715_E94E73CDF792B21860EBDDF8ECD1A580.jpg","sales":"0"},{"tuan_id":"4","title":"网鱼网咖","price":"19.00","market_price":"50.00","photo":"photo/201707/20170715_317A84DB71D05A7DCB4F62A3E42D56FF.jpg","sales":"1"}]
         * options : {"shop_id":"1","type":"1","config":[{"m":"50","d":"5"}],"discount":"95","max_youhui":"20","orders":"0"}
         * photo_count : 2
         */

        public String shop_id;
        public String title;
        public String phone;
        public String have_maidan;
        public String lat;
        public String lng;
        public String logo;
        public String banner;
        public String comments;
        public String addr;
        public String avg_amount;
        public String is_wifi;
        public String is_cart;
        public String comment_url;
        public String avg_score;
        public int collect;
        public ShareInfoBean share_info;
        public WaimaiBean waimai;
        public int other_count;
        public TuanBean tuan;
        public OptionsBean options;
        public String photo_count;
        public List<TuijianBean> tuijian;

        public static class ShareInfoBean implements Serializable{
            /**
             * title : 卡旺卡
             * desc : 卡旺卡团购，优惠多多！
             * link : http://o2o.jhcms.cn/tuan/shop/detail-1.html
             * imgUrl : photo/201705/20170512_694BBFE915ADA3E020A3172107AB4713.jpg
             */

            public String title;
            public String desc;
            public String link;
            public String imgUrl;
        }

        public static class WaimaiBean implements Serializable{
            /**
             * word : 外
             * title : 外卖
             * color : FF9900
             * url : http://waimai.o2o.jhcms.cn/shop/detail-1.html
             */

            public String word;
            public String title;
            public String color;
            public String url;
        }

        public static class TuanBean implements Serializable{
            /**
             * word : 团
             * title : 团购
             * color : EE6723
             * product : [{"tuan_id":"1","title":"方燕烤猪蹄","price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","sales":"9"},{"tuan_id":"2","title":"重庆·猪圈火锅","price":"89.00","market_price":"100.00","photo":"photo/201707/20170714_DF95D1209F67E821F09743CF97E01A07.jpg","sales":"0"}]
             */

            public String word;
            public String title;
            public String color;
            public List<ProductBean> product;

            public static class ProductBean implements Serializable{
                /**
                 * tuan_id : 1
                 * title : 方燕烤猪蹄
                 * price : 10.90
                 * market_price : 12.00
                 * photo : photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg
                 * sales : 9
                 */

                public String tuan_id;
                public String title;
                public String price;
                public String market_price;
                public String photo;
                public String sales;
            }
        }

        public static class OptionsBean implements Serializable{
            /**
             * shop_id : 1
             * type : 1
             * config : [{"m":"50","d":"5"}]
             * discount : 95
             * max_youhui : 20
             * orders : 0
             */

            public String shop_id;
            public String type;
            public double discount;
            public String max_youhui;
            public String orders;
            public List<ConfigBean> config;

            public static class ConfigBean implements Serializable{
                /**
                 * m : 50
                 * d : 5
                 */

                public String m;
                public String d;
            }
        }

        public static class TuijianBean implements Serializable {
            /**
             * tuan_id : 3
             * title : 胖帅肉蟹煲
             * price : 86.00
             * market_price : 100.00
             * photo : photo/201707/20170715_E94E73CDF792B21860EBDDF8ECD1A580.jpg
             * sales : 0
             */

            public String tuan_id;
            public String title;
            public String price;
            public String market_price;
            public String photo;
            public String sales;
        }
    }
}
