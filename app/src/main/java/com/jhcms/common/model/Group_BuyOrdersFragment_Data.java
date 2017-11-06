package com.jhcms.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */
public class Group_BuyOrdersFragment_Data implements Serializable {

    private List<Group_BuyOrdersFragment_Model> items;

    public List<Group_BuyOrdersFragment_Model> getItems() {
        return items;
    }

    public void setItems(List<Group_BuyOrdersFragment_Model> items) {
        this.items = items;
    }


}
