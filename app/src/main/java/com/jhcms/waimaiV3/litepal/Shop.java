package com.jhcms.waimaiV3.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by wangyujie
 * Date 2017/6/13
 * Time 21:48
 * TODO 数据库商家表
 */

public class Shop extends DataSupport {
    public String shop_id;
    private String shop_name;

    public String getProduct_info() {
        return product_info;
    }

    public void setProduct_info(String product_info) {
        this.product_info = product_info;
    }

    private String product_info;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }
}
