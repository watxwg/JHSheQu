package com.jhcms.mall.model;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/8/24 18:04
 * 描述：商品商家列表数据model
 */

public class ItemsModel<T>{
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
