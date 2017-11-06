package com.jhcms.waimaiV3.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/9.
 * TODO
 */

public class ShopCartGood implements Serializable {


    /**
     * bad : 0
     * count : 2
     * good : 0
     * is_spec : 1
     * logo :
     * name : 店(大)
     * price : 2.00
     * productId : 54
     * product_id : 5461
     * productsEntity : {"bad":"0","cate_id":"50","good":"0","is_spec":"1","package_price":"1.00","photo":"photo/201706/20170615_BDBB34904610C73E2BC4BA0FC46F87D5.png","price":"2.00","product_id":"54","sale_sku":218,"sale_type":"1","sales":"4","shop_id":"26","specs":[{"package_price":"1.00","price":"2.00","product_id":"54","sale_count":"3","sale_sku":108,"spec_id":"61","spec_name":"大","spec_photo":""},{"package_price":"1.00","price":"1.00","product_id":"54","sale_count":"1","sale_sku":110,"spec_id":"62","spec_name":"小","spec_photo":""}],"title":"店"}
     * sale_sku : 108
     * shop_id : 26
     * specSelect : 0
     * spec_id : 61
     * typeId : 0
     */

    public String bad;
    public int count;
    public String good;
    public String is_spec;
    public String logo;
    public String name;
    public String price;
    public String productId;
    public String product_id;
    public ProductsEntityEntity productsEntity;
    public int sale_sku;
    public String shop_id;
    public int specSelect;
    public String spec_id;
    public int typeId;

    public static class ProductsEntityEntity {
        /**
         * bad : 0
         * cate_id : 50
         * good : 0
         * is_spec : 1
         * package_price : 1.00
         * photo : photo/201706/20170615_BDBB34904610C73E2BC4BA0FC46F87D5.png
         * price : 2.00
         * product_id : 54
         * sale_sku : 218
         * sale_type : 1
         * sales : 4
         * shop_id : 26
         * specs : [{"package_price":"1.00","price":"2.00","product_id":"54","sale_count":"3","sale_sku":108,"spec_id":"61","spec_name":"大","spec_photo":""},{"package_price":"1.00","price":"1.00","product_id":"54","sale_count":"1","sale_sku":110,"spec_id":"62","spec_name":"小","spec_photo":""}]
         * title : 店
         */

        public String bad;
        public String cate_id;
        public String good;
        public String is_spec;
        public String package_price;
        public String photo;
        public String price;
        public String product_id;
        public int sale_sku;
        public String sale_type;
        public String sales;
        public String shop_id;
        public String title;
        public List<SpecsEntity> specs;

        public static class SpecsEntity {
            /**
             * package_price : 1.00
             * price : 2.00
             * product_id : 54
             * sale_count : 3
             * sale_sku : 108
             * spec_id : 61
             * spec_name : 大
             * spec_photo :
             */

            public String package_price;
            public String price;
            public String product_id;
            public String sale_count;
            public int sale_sku;
            public String spec_id;
            public String spec_name;
            public String spec_photo;
        }
    }
}
