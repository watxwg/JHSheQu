package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * Created by Wyj on 2017/4/20.
 */
public class LabelAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> data;
    private int checkItemPosition = -1;
    private int selectPosition;


    public LabelAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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

    public void  setPosition(int checkItemPosition) {
        this.checkItemPosition = checkItemPosition;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LabelViewHolder holder = null;
        if (convertView == null) {
            holder = new LabelViewHolder();
            convertView = inflater.inflate(R.layout.adapter_label_item, parent, false);
            holder.tvLabel = (TextView) convertView.findViewById(R.id.tv_label);
            convertView.setTag(holder);
        } else {
            holder = (LabelViewHolder) convertView.getTag();
        }
        holder.tvLabel.setText(data.get(position));
        if (position == checkItemPosition) {
            holder.tvLabel.setSelected(true);
        } else {
            holder.tvLabel.setSelected(false);
        }

        return convertView;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class LabelViewHolder {
        TextView tvLabel;

    }
}
