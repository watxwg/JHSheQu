package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/14.
 */
public class ProductModle implements Serializable {

    /**
     * product_id : 13
     * product_name : 鸡翅饭
     * product_price : 12.00
     * package_price : 1.00
     * product_number : 1
     * spec_id : 0
     */

    private String product_id;
    private String product_name;
    private String product_price;
    private String package_price;
    private String product_number;
    private String spec_id;
    private String pingjia;

    public int getSale_sku() {
        return sale_sku;
    }

    public void setSale_sku(int sale_sku) {
        this.sale_sku = sale_sku;
    }

    private int sale_sku;

    public String getPingjia() {
        return pingjia;
    }

    public void setPingjia(String pingjia) {
        this.pingjia = pingjia;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
    }
}
