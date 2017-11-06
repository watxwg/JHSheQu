package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/5.
 */
public class OptionAdapter extends BaseAdapter {

    Context context;
    String[] infos;

    public OptionAdapter(Context context) {
        this.context = context;
    }

    public void setInfos(String[] infos) {
        this.infos = infos;
    }

    @Override
    public int getCount() {
        return infos == null?0:infos.length;
    }

    @Override
    public Object getItem(int i) {
        return infos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_option, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.mTitleTv.setText(infos[i]);

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.title_tv)
        TextView mTitleTv;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
