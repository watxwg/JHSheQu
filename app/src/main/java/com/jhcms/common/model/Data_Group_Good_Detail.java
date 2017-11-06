package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin
 * on 2017/8/1.
 * TODO
 */

public class Data_Group_Good_Detail implements Serializable {

    /**
     * detail : {"tuan_id":"1","shop_id":"1","title":"方燕烤猪蹄","desc":["免费提供蜡烛1包+刀1把+小盘1包","可在蛋糕上写8个字","免费提供包装"],"price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","info_date":"2017.07.01-2017.08.31","info_week":"0","info_time":"7:00-24:00","notice":["免费提供餐巾纸","每张团购券最多1人使用","无需预约，如遇消费高峰时段您可能需要排队","只适用于初次到店非商户会员使用","不再与其他优惠同享"],"detail":"<img src=\"http://biz.o2o.jhcms.cn/static/kindeditor/plugins/emoticons/images/21.gif\" border=\"0\" alt=\"\" />","sales":"24","min_buy":"1","max_buy":"5","stock_num":"75","collect":"0","comment_url":"http://o2o.jhcms.cn/tuan/comment/index-1.html","tuwen_url":"http://o2o.jhcms.cn/tuan/product/tuwendetail-1.html","share_info":{"title":"方燕烤猪蹄","desc":"免费提供蜡烛1包+刀1把+小盘1包,可在蛋糕上写8个字,免费提供包装","link":"http://o2o.jhcms.cn/tuan/product/detail-1.html","imgUrl":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg"},"tuijian":[{"tuan_id":"1","title":"方燕烤猪蹄","price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","sales":"24"},{"tuan_id":"2","title":"重庆·猪圈火锅","price":"89.00","market_price":"100.00","photo":"photo/201707/20170714_DF95D1209F67E821F09743CF97E01A07.jpg","sales":"18"},{"tuan_id":"3","title":"胖帅肉蟹煲","price":"86.00","market_price":"100.00","photo":"photo/201707/20170715_E94E73CDF792B21860EBDDF8ECD1A580.jpg","sales":"3"}],"shop":{"shop_id":"1","title":"卡旺卡","phone":"13901010101","lat":"31.835824","lng":"117.257184","comments":"34","addr":"合肥市蜀山区华润五彩城国际","avg_score":"3.9"}}
     */

    public DetailBean detail;

    public static class DetailBean implements Serializable {
        /**
         * tuan_id : 1
         * shop_id : 1
         * title : 方燕烤猪蹄
         * desc : ["免费提供蜡烛1包+刀1把+小盘1包","可在蛋糕上写8个字","免费提供包装"]
         * price : 10.90
         * market_price : 12.00
         * photo : photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg
         * info_date : 2017.07.01-2017.08.31
         * info_week : 0
         * info_time : 7:00-24:00
         * notice : ["免费提供餐巾纸","每张团购券最多1人使用","无需预约，如遇消费高峰时段您可能需要排队","只适用于初次到店非商户会员使用","不再与其他优惠同享"]
         * detail : <img src="http://biz.o2o.jhcms.cn/static/kindeditor/plugins/emoticons/images/21.gif" border="0" alt="" />
         * sales : 24
         * min_buy : 1
         * max_buy : 5
         * stock_num : 75
         * collect : 0
         * comment_url : http://o2o.jhcms.cn/tuan/comment/index-1.html
         * tuwen_url : http://o2o.jhcms.cn/tuan/product/tuwendetail-1.html
         * share_info : {"title":"方燕烤猪蹄","desc":"免费提供蜡烛1包+刀1把+小盘1包,可在蛋糕上写8个字,免费提供包装","link":"http://o2o.jhcms.cn/tuan/product/detail-1.html","imgUrl":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg"}
         * tuijian : [{"tuan_id":"1","title":"方燕烤猪蹄","price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","sales":"24"},{"tuan_id":"2","title":"重庆·猪圈火锅","price":"89.00","market_price":"100.00","photo":"photo/201707/20170714_DF95D1209F67E821F09743CF97E01A07.jpg","sales":"18"},{"tuan_id":"3","title":"胖帅肉蟹煲","price":"86.00","market_price":"100.00","photo":"photo/201707/20170715_E94E73CDF792B21860EBDDF8ECD1A580.jpg","sales":"3"}]
         * shop : {"shop_id":"1","title":"卡旺卡","phone":"13901010101","lat":"31.835824","lng":"117.257184","comments":"34","addr":"合肥市蜀山区华润五彩城国际","avg_score":"3.9"}
         */

        public String tuan_id;
        public String shop_id;
        public String title;
        public String price;
        public String market_price;
        public String photo;
        public String info_date;
        public String info_week;
        public String info_time;
        public String detail;
        public String sales;
        public int min_buy;
        public int max_buy;
        public String stock_num;
        public int collect;
        public String comment_url;
        public String tuwen_url;
        public ShareInfoBean share_info;
        public ShopBean shop;
        public List<String> desc;
        public List<String> notice;
        public List<Data_Group_Shop_Detail.DetailBean.TuijianBean> tuijian;

        public static class ShareInfoBean implements Serializable{
            /**
             * title : 方燕烤猪蹄
             * desc : 免费提供蜡烛1包+刀1把+小盘1包,可在蛋糕上写8个字,免费提供包装
             * link : http://o2o.jhcms.cn/tuan/product/detail-1.html
             * imgUrl : photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg
             */

            public String title;
            public String desc;
            public String link;
            public String imgUrl;
        }

        public static class ShopBean implements Serializable{
            /**
             * shop_id : 1
             * title : 卡旺卡
             * phone : 13901010101
             * lat : 31.835824
             * lng : 117.257184
             * comments : 34
             * addr : 合肥市蜀山区华润五彩城国际
             * avg_score : 3.9
             */

            public String shop_id;
            public String title;
            public String phone;
            public String lat;
            public String lng;
            public String comments;
            public String addr;
            public String avg_score;
        }
    }
}
