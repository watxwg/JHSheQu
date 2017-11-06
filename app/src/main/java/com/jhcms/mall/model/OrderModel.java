package com.jhcms.mall.model;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/19 18:03
 * 描述：创建订单的返回信息
 */

public class OrderModel {
    private List<String> order_ids;

    public List<String> getOrder_ids() {
        return order_ids;
    }

    public void setOrder_ids(List<String> order_ids) {
        this.order_ids = order_ids;
    }
}
