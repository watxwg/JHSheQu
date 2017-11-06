package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/15.
 */
public class Data_Orederdetail implements Serializable {
    private  OrderdetailModel detail;

    public OrderdetailModel getDetail() {
        return detail;
    }

    public void setDetail(OrderdetailModel detail) {
        this.detail = detail;
    }
}
