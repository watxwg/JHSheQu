package com.jhcms.common.model;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/29.
 * TODO天降红包
 */

public class ShopHongBao {

    /**
     * title : 天降红包
     * intro : 天上掉红包,明天可能还会有哦!
     * items : [{"min_amount":"20","amount":"3","title":"烛光晚餐","dateline":"1498838399"},{"min_amount":"30","amount":"5","title":"深夜虾搞","dateline":"1498838399"}]
     */

    public String title;
    public String intro;
    public List<ItemsEntity> items;

    public static class ItemsEntity {
        /**
         * min_amount : 20
         * amount : 3
         * title : 烛光晚餐
         * dateline : 1498838399
         */

        public String min_amount;
        public String amount;
        public String title;
        public String dateline;
    }
}
