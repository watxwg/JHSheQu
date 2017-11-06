package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/22.
 */
public class Response_TuanOrderDetailsActivity extends  SharedResponse implements Serializable {
    private  TuanOrderDetailsActivity_Data data;

    public TuanOrderDetailsActivity_Data getData() {
        return data;
    }

    public void setData(TuanOrderDetailsActivity_Data data) {
        this.data = data;
    }
}
