package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/5/27.
 * TODO
 */

public class OrderCancelAdapter extends BaseAdapter {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<String> data;
    private int selectPostion = -1;

    public OrderCancelAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.dialog_cancel_item, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = Utils.getScreenW(context) * 4 / 15 - Utils.dip2px(context, 10);
        holder.tvLabel.setLayoutParams(params);
        holder.tvLabel.setText(data.get(position));
        if (selectPostion == position) {
            holder.tvLabel.setSelected(true);
        } else {
            holder.tvLabel.setSelected(false);
        }

        return convertView;
    }

    public void setData(List<String> data) {
        Logger.d("data" + data.size());
        this.data = data;
        Logger.d("this.data" + this.data.size());
        notifyDataSetChanged();
    }

    public void setSelectPostion(int postion) {
        selectPostion = postion;
        notifyDataSetChanged();
    }

    public class MyViewHolder {
        @Bind(R.id.tv_label)
        TextView tvLabel;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
