package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/15 17:12
 * 描述：规格详细信息
 */

public class SpecificaitonDetailModel {

    /**
     * attr_stock_id : 175
     * product_id : 23
     * stock_name : 100
     * price : 45.00
     * wei_price : 40.00
     * photo : photo/201705/20170527_9A81C63F57073020F8ABA64DC5C120C7.jpeg
     * stock : 26
     * stock_sku :
     * sales : 4
     * stock_real_name : 小份
     */

    private String attr_stock_id;
    private String product_id;
    private String stock_name;
    private String price;
    private String wei_price;
    private String photo;
    private String stock;
    private String stock_sku;
    private String sales;
    private String stock_real_name;

    public String getAttr_stock_id() {
        return attr_stock_id;
    }

    public void setAttr_stock_id(String attr_stock_id) {
        this.attr_stock_id = attr_stock_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStock_sku() {
        return stock_sku;
    }

    public void setStock_sku(String stock_sku) {
        this.stock_sku = stock_sku;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getStock_real_name() {
        return stock_real_name;
    }

    public void setStock_real_name(String stock_real_name) {
        this.stock_real_name = stock_real_name;
    }
}
