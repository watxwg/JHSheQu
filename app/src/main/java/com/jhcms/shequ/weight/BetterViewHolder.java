package com.jhcms.shequ.weight;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wangyujie
 * Date 2017/6/1.
 * TODO
 */

public abstract class BetterViewHolder<T extends Visitable> extends RecyclerView.ViewHolder {

    public BetterViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindItem(T t);
}