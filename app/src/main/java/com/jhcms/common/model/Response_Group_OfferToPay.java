package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/24.
 */
public class Response_Group_OfferToPay extends SharedResponse implements Serializable {
 private    Group_OfferToPay_Data  data;

    public Group_OfferToPay_Data getData() {
        return data;
    }

    public void setData(Group_OfferToPay_Data data) {
        this.data = data;
    }
}
