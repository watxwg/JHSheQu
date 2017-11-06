package com.jhcms.common.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/7/24.
 */
public class Group_OfferToPay_Data implements Serializable {

    private ArrayList<Group_OfferToPay_model> items;

    public ArrayList<Group_OfferToPay_model> getItems() {
        return items;
    }

    public void setItems(ArrayList<Group_OfferToPay_model> items) {
        this.items = items;
    }
}
