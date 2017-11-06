package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/15.
 */
public class Response_Orederdetail extends SharedResponse implements Serializable {

    private Data_Orederdetail data;

    public Data_Orederdetail getData() {
        return data;
    }

    public void setData(Data_Orederdetail data) {
        this.data = data;
    }
}
