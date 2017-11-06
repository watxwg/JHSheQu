package com.jhcms.mall.model;

/**
 * Created by wangyujie
 * on 2017/9/15.17:42
 * TODO  店铺信息
 */


public class StoreInfo {
    protected String shopId;
    protected String name;
    protected boolean isChoosed;


    public StoreInfo(String id, String name) {
        shopId = id;
        this.name = name;
    }

    public String getShopId() {
        return shopId;
    }

    public void setId(String id) {
        shopId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }
}
