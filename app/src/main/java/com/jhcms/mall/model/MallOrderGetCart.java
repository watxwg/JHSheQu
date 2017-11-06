package com.jhcms.mall.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by wangyujie
 * on 2017/9/13.17:27
 * TODO 获取购物车
 */

public class MallOrderGetCart {


    private List<CartGoodsBean> cart_goods;

    public List<CartGoodsBean> getCart_goods() {
        return cart_goods;
    }

    public void setCart_goods(List<CartGoodsBean> cart_goods) {
        this.cart_goods = cart_goods;
    }

    public static class CartGoodsBean {
        /**
         * shop_id : 1
         * title : 卡旺卡(商城)
         * carts : [{"product_id":"23","number":"3","stock_name":"99","wei_price":"40.00","photo":"photo/201705/20170527_BD51ABFBCF20BA957E1D9D9369ADF6E6.jpg","stock_real_name":"大份","freight_type":"1","title":"测试商品","freight":"0.00"},{"product_id":"23","number":"1","stock_name":"100","wei_price":"40.00","photo":"photo/201705/20170527_9A81C63F57073020F8ABA64DC5C120C7.jpeg","stock_real_name":"小份","freight_type":"1","title":"测试商品","freight":"0.00"}]
         */

        private String shop_id;
        private String title;
        private List<CartsBean> carts;

        public static CartGoodsBean objectFromData(String str) {

            return new Gson().fromJson(str, CartGoodsBean.class);
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<CartsBean> getCarts() {
            return carts;
        }

        public void setCarts(List<CartsBean> carts) {
            this.carts = carts;
        }

        public static class CartsBean {
            /**
             * product_id : 23
             * number : 3
             * stock_name : 99
             * wei_price : 40.00
             * photo : photo/201705/20170527_BD51ABFBCF20BA957E1D9D9369ADF6E6.jpg
             * stock_real_name : 大份
             * freight_type : 1
             * title : 测试商品
             * freight : 0.00
             */

            private String product_id;
            private int number;
            private String stock_name;
            private double wei_price;
            private String photo;
            private String stock_real_name;
            private String freight_type;
            private String title;
            private String freight;

            public static CartsBean objectFromData(String str) {

                return new Gson().fromJson(str, CartsBean.class);
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getStock_name() {
                return stock_name;
            }

            public void setStock_name(String stock_name) {
                this.stock_name = stock_name;
            }

            public double getWei_price() {
                return wei_price;
            }

            public void setWei_price(double wei_price) {
                this.wei_price = wei_price;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getStock_real_name() {
                return stock_real_name;
            }

            public void setStock_real_name(String stock_real_name) {
                this.stock_real_name = stock_real_name;
            }

            public String getFreight_type() {
                return freight_type;
            }

            public void setFreight_type(String freight_type) {
                this.freight_type = freight_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }
        }
    }
}
