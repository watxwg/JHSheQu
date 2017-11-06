package com.jhcms.waimaiV3.model;


import com.jhcms.common.model.ShopDetail;

import java.io.Serializable;

/**
 * Created by wangyujie
 * Date 2017/6/9.
 * TODO
 */

public class Goods implements Serializable {

    public String getPagePrice() {
        return pagePrice;
    }

    public void setPagePrice(String pagePrice) {
        this.pagePrice = pagePrice;
    }

    public String pagePrice;

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public String headTitle;

    @Override
    public String toString() {
        return "Goods{" +
                "productsEntity=" + productsEntity +
                ", product_id='" + product_id + '\'' +
                ", productId='" + productId + '\'' +
                ", shop_id='" + shop_id + '\'' +
                ", spec_id='" + spec_id + '\'' +
                ", price='" + price + '\'' +
                ", typeId=" + typeId +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", sale_sku=" + sale_sku +
                ", is_spec='" + is_spec + '\'' +
                ", good='" + good + '\'' +
                ", bad='" + bad + '\'' +
                ", logo='" + logo + '\'' +
                ", specSelect=" + specSelect +
                '}';
    }

    public ShopDetail.ItemsEntity.ProductsEntity productsEntity;
    /*商品Id*/
    public String product_id;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String productId;
    /*商户Id*/
    public String shop_id;
    /*规格Id*/
    public String spec_id;
    /*商品价格*/
    public String price;
    /*种类Id*/
    public int typeId;
    /*种类名字*/
    public String title;
    /*商品名称*/
    public String name;
    /*商品数量*/
    public int count;
    /*商品规格总数量*/
//    public int specCount;
    /*商品库存*/
    public int sale_sku;
    /*是否有规格*/
    public String is_spec;
    /*好评数*/
    public String good;
    /*差评数*/
    public String bad;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String logo;
    /*选中的规格位置*/
    public int specSelect;

    public void setName(String name) {
        this.name = name;
    }

    public void setProductsEntity(ShopDetail.ItemsEntity.ProductsEntity productsEntity) {
        this.productsEntity = productsEntity;
    }


    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setSale_sku(int sale_sku) {
        this.sale_sku = sale_sku;
    }

    public void setIs_spec(String is_spec) {
        this.is_spec = is_spec;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public void setSpecSelect(int specSelect) {
        this.specSelect = specSelect;
    }


    public Goods(ShopDetail.ItemsEntity.ProductsEntity productsEntity, String product_id, String title, int typeId) {
        this.productsEntity = productsEntity;
        this.product_id = product_id;
        this.typeId = typeId;
        this.title = title;
    }

    public Goods() {

    }

    /*public Goods(ShopDetail.ItemsEntity.ProductsEntity productsEntity, List<ShopDetail.ItemsEntity.ProductsEntity.SpecsEntity> specs, String title, int typeId) {
        this.productsEntity = productsEntity;
        this.SpecsEntity = specs;
        this.typeId = typeId;
        this.title = title;
    }*/
}
