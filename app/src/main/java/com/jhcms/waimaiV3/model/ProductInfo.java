package com.jhcms.waimaiV3.model;

/**
 * Created by wangyujie
 * Date 2017/6/20.
 * TODO
 */

public class ProductInfo {
    /*商品Id*/
    public String product_id;
    /*商户Id*/
    public String shop_id;
    /*规格Id*/
    public int spec_id;
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
    /*商品库存*/
    public int sale_sku;
    /*是否有规格*/
    public String is_spec;
    /*好评数*/
    public String good;
    /*差评数*/
    public String bad;

    public ProductInfo() {
        super();
    }
}
