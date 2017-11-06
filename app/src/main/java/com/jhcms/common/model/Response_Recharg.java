package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/26.
 */
public class Response_Recharg extends SharedResponse implements Serializable {
    private  Data_Recharge  data;

    public Data_Recharge getData() {
        return data;
    }

    public void setData(Data_Recharge data) {
        this.data = data;
    }
}
