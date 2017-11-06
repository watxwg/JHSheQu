package com.jhcms.shequ.weight;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */

public class ProductList implements Visitable {

    public List<Product> products;

    public ProductList(@NonNull List<Product> products) {this.products = products;}

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}

