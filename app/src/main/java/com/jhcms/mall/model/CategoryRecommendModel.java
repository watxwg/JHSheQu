package com.jhcms.mall.model;

import com.jhcms.waimaiV3.litepal.Shop;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/11 16:59
 * 描述：分类推荐接口数据模型
 */

public class CategoryRecommendModel {
    private List<CategoryModel> items;
    private List<ProductItemModel> hot_products;
    private List<ShopItemModel> hot_shops;
    private String more_shop_url;
    private String more_product_url;

    public List<CategoryModel> getItems() {
        return items;
    }

    public void setItems(List<CategoryModel> items) {
        this.items = items;
    }

    public List<ProductItemModel> getHot_products() {
        return hot_products;
    }

    public void setHot_products(List<ProductItemModel> hot_products) {
        this.hot_products = hot_products;
    }

    public List<ShopItemModel> getHot_shops() {
        return hot_shops;
    }

    public void setHot_shops(List<ShopItemModel> hot_shops) {
        this.hot_shops = hot_shops;
    }

    public String getMore_shop_url() {
        return more_shop_url;
    }

    public void setMore_shop_url(String more_shop_url) {
        this.more_shop_url = more_shop_url;
    }

    public String getMore_product_url() {
        return more_product_url;
    }

    public void setMore_product_url(String more_product_url) {
        this.more_product_url = more_product_url;
    }
}
