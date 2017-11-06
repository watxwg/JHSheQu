package com.jhcms.shequ.weight;

import android.view.View;

import com.jhcms.waimaiV3.R;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */

public class ListTypeFactory implements TypeFactory {
    @Override
    public int type(Category title) {
        return R.layout.layout_list_item_category;
    }

    @Override
    public int type(ProductList products) {
        return R.layout.layout_item_list;
    }


    @Override
    public BetterViewHolder onCreateViewHolder(View itemView, int viewType) {
        BetterViewHolder viewHolder = null;
        switch (viewType) {
            case R.layout.layout_list_item_category:
                viewHolder = new CategoryViewHolder(itemView);
                break;
            case R.layout.layout_item_list:
                viewHolder = new ProductListViewHolder(itemView);
                break;
            default:
                break;
        }
        return viewHolder;
    }
}
