package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/24.
 */
public class Response_LookTuanOrderDetailsActivity extends  SharedResponse implements Serializable {
    private  LookTuanOrderDetailsActivity_data data;

    public LookTuanOrderDetailsActivity_data getData() {
        return data;
    }

    public void setData(LookTuanOrderDetailsActivity_data data) {
        this.data = data;
    }
}
