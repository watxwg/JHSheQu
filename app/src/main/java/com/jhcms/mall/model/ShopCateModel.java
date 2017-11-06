package com.jhcms.mall.model;

/**
 * 作者：WangWei
 * 日期：2017/9/18 16:21
 * 描述：商家内的分类
 */

public class ShopCateModel {

    /**
     * cate_id : 9
     * parent_id : 0
     * shop_id : 7
     * title : 数码产品
     * icon :
     * orderby : 0
     * dateline : 1495612425
     */

    private String cate_id;
    private String parent_id;
    private String shop_id;
    private String title;
    private String icon;
    private String orderby;
    private String dateline;

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
}
