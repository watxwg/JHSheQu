package com.jhcms.waimaiV3.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by wangyujie
 * Date 2017/7/8.
 * TODO
 */

public class Product extends DataSupport {

    public String title;
    private String goodInfo;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String shopId;

    public String getGoodInfo() {

        return goodInfo;
    }

    public void setGoodInfo(String goodInfo) {
        this.goodInfo = goodInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*private String is_spec;
    private String product_id;
    private String photo;
    private String sale_sku;*/
}
