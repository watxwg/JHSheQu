package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/21.
 */
public class Response_Mine extends SharedResponse implements Serializable {
    private Data_Mine data;

    public Data_Mine getData() {
        return data;
    }

    public void setData(Data_Mine data) {
        this.data = data;
    }
}
