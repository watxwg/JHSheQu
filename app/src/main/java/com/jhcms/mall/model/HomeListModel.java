package com.jhcms.mall.model;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/8/21 10:38
 * 描述：首页列表数据
 */

public class HomeListModel {
    //更多商家链接
    public String more_shop_url;
    //更多商品链接
    public String more_product_url;
    //广告图片
    public List<ImageInfoModel> adv;
    //cate列表
    public List<CateModel> index_cate;
    //轮播图数据
    public List<ImageInfoModel> banner;
    //商家列表数据
    public List<ShopItemModel> shop_items;
    //商品列表数据
    public List<ProductItemModel> product_items;

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

    public List<ImageInfoModel> getAdv() {
        return adv;
    }

    public void setAdv(List<ImageInfoModel> adv) {
        this.adv = adv;
    }

    public List<CateModel> getIndex_cate() {
        return index_cate;
    }

    public void setIndex_cate(List<CateModel> index_cate) {
        this.index_cate = index_cate;
    }

    public List<ImageInfoModel> getBanner() {
        return banner;
    }

    public void setBanner(List<ImageInfoModel> banner) {
        this.banner = banner;
    }

    public List<ShopItemModel> getShop_items() {
        return shop_items;
    }

    public void setShop_items(List<ShopItemModel> shop_items) {
        this.shop_items = shop_items;
    }

    public List<ProductItemModel> getProduct_items() {
        return product_items;
    }

    public void setProduct_items(List<ProductItemModel> product_items) {
        this.product_items = product_items;
    }
}
