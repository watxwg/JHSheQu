package com.jhcms.shequ.weight;

import android.view.View;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */

public interface TypeFactory {
    int type(Category title);
    int type(ProductList products);

    BetterViewHolder onCreateViewHolder(View itemView, int viewType);
}
