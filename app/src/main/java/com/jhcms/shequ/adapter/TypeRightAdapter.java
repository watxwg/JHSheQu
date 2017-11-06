package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.common.widget.GridViewForScrollView;
import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/5.
 */
public class TypeRightAdapter extends BaseAdapter {

    Context context;
    TypeAdapter typeAdapter;

    public TypeRightAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_type_right, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        typeAdapter = new TypeAdapter(context);
        holder.mTypeGv.setAdapter(typeAdapter);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.title_tv)
        TextView mTitleTv;
        @Bind(R.id.type_gv)
        GridViewForScrollView mTypeGv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
