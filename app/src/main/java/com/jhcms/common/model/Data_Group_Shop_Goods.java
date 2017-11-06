package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin
 * on 2017/7/25.
 * TODO
 */

public class Data_Group_Shop_Goods implements Serializable {


    /**
     * data : {"items":[{"tuan_id":"1","title":"方燕烤猪蹄","price":"10.90","market_price":"12.00","photo":"photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg","sales":"24"},{"tuan_id":"2","title":"重庆·猪圈火锅","price":"89.00","market_price":"100.00","photo":"photo/201707/20170714_DF95D1209F67E821F09743CF97E01A07.jpg","sales":"18"},{"tuan_id":"3","title":"胖帅肉蟹煲","price":"86.00","market_price":"100.00","photo":"photo/201707/20170715_E94E73CDF792B21860EBDDF8ECD1A580.jpg","sales":"4"}]}
     * error : 0
     * message : success
     */

    public DataBean data;
    public String error;
    public String message;

    public static class DataBean {
        public List<ItemsBean> items;

        public static class ItemsBean {
            /**
             * tuan_id : 1
             * title : 方燕烤猪蹄
             * price : 10.90
             * market_price : 12.00
             * photo : photo/201707/20170714_476AEB28646BE4007FDD57C1C404E63B.jpg
             * sales : 24
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
