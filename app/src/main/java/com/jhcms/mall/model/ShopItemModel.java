package com.jhcms.mall.model;

import com.jhcms.mall.model.interfaces.ProductInfoInterface;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/8/21 10:45
 * 描述：商家列表数据
 */

public class ShopItemModel {


    /**
     * shop_id : 7
     * title : 杂货铺
     * logo : photo/201705/20170524_CB47B7DC874F8A25BCB5CF8550AB76DA.jpg
     */

    private String shop_id;
    private String title;
    private String logo;
    private String collects;
    private String orders;
    private List<ProductInfo> products;
    private List<ProductInfo> re_products;

    public List<ProductInfo> getRe_products() {
        return re_products;
    }

    public void setRe_products(List<ProductInfo> re_products) {
        this.re_products = re_products;
    }

    public String getCollects() {
        return collects;
    }

    public void setCollects(String collects) {
        this.collects = collects;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfo> products) {
        this.products = products;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public static class ProductInfo implements ProductInfoInterface{
        private String product_id;
        private String photo;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        private String shop_id;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        @Override
        public String getPhotoIn() {
            return photo;
        }

        @Override
        public String getProductIdIn() {
            return product_id;
        }
    }
}
