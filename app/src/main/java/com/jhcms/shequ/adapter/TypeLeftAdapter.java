package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/5.
 */
public class TypeLeftAdapter extends BaseAdapter {

    Context context;
    public int curPosition = 0;

    public TypeLeftAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_type_left, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

     //   holder.mNameTv.setText(products.get(position).title);
        if (i == curPosition) {
            holder.mNameTv.setTextColor(context.getResources().getColor(
                    R.color.themColor));
            holder.mLeftLl.setBackgroundResource(R.color.white);
            holder.mChooseLine.setBackgroundResource(R.color.themColor);
        } else {
            holder.mNameTv.setTextColor(context.getResources().getColor(
                    R.color.black));
            holder.mLeftLl.setBackgroundResource(R.color.gray);
            holder.mChooseLine.setBackgroundResource(0);
        }

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.choose_line)
        View mChooseLine;
        @Bind(R.id.name_tv)
        TextView mNameTv;
        @Bind(R.id.left_ll)
        LinearLayout mLeftLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
