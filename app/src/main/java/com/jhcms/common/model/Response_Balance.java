package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/24.
 */
public class Response_Balance extends SharedResponse implements Serializable{
    private  Data_Balancemodel data;

    public Data_Balancemodel getData() {
        return data;
    }

    public void setData(Data_Balancemodel data) {
        this.data = data;
    }

}
