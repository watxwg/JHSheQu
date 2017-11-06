package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/25.
 */
public class Response_GroupOfferToPayOrderDeatail extends SharedResponse implements Serializable {
    private  GroupOfferToPayOrderDeatailData data;

    public GroupOfferToPayOrderDeatailData getData() {
        return data;
    }

    public void setData(GroupOfferToPayOrderDeatailData data) {
        this.data = data;
    }
}
