package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/8/21 10:56
 * 描述：商品列表数据
 */

public class ProductItemModel {


    /**
     * product_id : 2 商品id
     * title : 测试商品1 商品名称
     * photo : photo/201705/20170510_62A59E5DF16ABDCC3F34843DA48178DF.jpeg 图片
     * price : 25.00 原价
     * wei_price : 19.50 微信价
     * shop_title : 商家名称
     */

    private String product_id;
    private String title;
    private String photo;
    private String price;
    private String wei_price;
    private String shop_title;
    private String sales;

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWei_price() {
        return wei_price;
    }

    public void setWei_price(String wei_price) {
        this.wei_price = wei_price;
    }

    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(String shop_title) {
        this.shop_title = shop_title;
    }
}
