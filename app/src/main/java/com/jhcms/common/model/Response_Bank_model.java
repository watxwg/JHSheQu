package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/9/15.
 */
public class Response_Bank_model extends SharedResponse implements Serializable {
    ArrayList<BankModel> data;

    public ArrayList<BankModel> getData() {
        return data;
    }

    public void setData(ArrayList<BankModel> data) {
        this.data = data;
    }
}
