package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/22.
 */
public class TuanOrderDetailsActivity_Data implements Serializable {
    private TuanOrderDetailsActivity_Model order;

    public TuanOrderDetailsActivity_Model getOrder() {
        return order;
    }

    public void setOrder(TuanOrderDetailsActivity_Model order) {
        this.order = order;
    }
}
