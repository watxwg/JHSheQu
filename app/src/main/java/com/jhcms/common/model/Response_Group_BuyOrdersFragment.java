package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/21.
 */
public class Response_Group_BuyOrdersFragment extends SharedResponse implements Serializable {
    private  Group_BuyOrdersFragment_Data data;

    public Group_BuyOrdersFragment_Data getData() {
        return data;
    }

    public void setData(Group_BuyOrdersFragment_Data data) {
        this.data = data;
    }
}
