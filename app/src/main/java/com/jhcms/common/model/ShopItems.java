package com.jhcms.common.model;

import java.util.List;

/**
 * Created by Wyj on 2017/3/23.
 */
public class ShopItems {

    /**
     * products : [{"product_id":"5","photo":"photo/201705/20170513_1AA467297845F2934D61B41444252F86.jpg","price":"59.00"},{"product_id":"6","photo":"photo/201705/20170513_B23E690EA352BA6C1DFFA72AA43A5764.jpg","price":"69.00"}]
     * waimai : {"shop_id":"2","title":"棒约翰披萨","logo":"photo/201705/20170512_F4C676B4239F79B6DB43F315E0D98C57.jpg","orders":"39","min_amount":"1","freight":"2","pei_type":"0","pei_time":"30","yy_status":"1","is_new":"0","yysj_status":"1","huodong":[{"title":"首单立减10元","word":"首","color":"20AD20"}],"avg_score":"3.33","juli":"960.52","juli_label":"<100m"}
     */

    public WaimaiEntity waimai;
    public List<ProductsEntity> products;

    public static class WaimaiEntity {
        /**
         * shop_id : 2
         * title : 棒约翰披萨
         * logo : photo/201705/20170512_F4C676B4239F79B6DB43F315E0D98C57.jpg
         * orders : 39
         * min_amount : 1
         * freight : 2
         * pei_type : 0
         * pei_time : 30
         * yy_status : 1
         * is_new : 0
         * yysj_status : 1
         * huodong : [{"title":"首单立减10元","word":"首","color":"20AD20"}]
         * avg_score : 3.33
         * juli : 960.52
         * juli_label : <100m
         */

        public String shop_id;
        public String title;
        public String logo;
        public String orders;
        public String min_amount;
        public String freight;
        public String pei_type;
        public String pei_time;
        public String yy_status;
        public String is_new;
        public String yysj_status;
        public String avg_score;
        public String juli;
        public String juli_label;
        public List<HuodongEntity> huodong;


        public static class HuodongEntity {
            /**
             * title : 首单立减10元
             * word : 首
             * color : 20AD20
             */

            public String title;
            public String word;
            public String color;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class ProductsEntity {
        /**
         * product_id : 5
         * photo : photo/201705/20170513_1AA467297845F2934D61B41444252F86.jpg
         * price : 59.00
         */

        public String product_id;
        public String photo;
        public String price;


    }
}
