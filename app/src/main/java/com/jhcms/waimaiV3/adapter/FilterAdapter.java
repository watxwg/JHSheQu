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
 * Created by Wyj on 2017/4/21.
 */
public class FilterAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private int checkItemPosition = -1;
    private int selectPosition;
    private List<String> data;

    public FilterAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public void  setPosition(int checkItemPosition) {
        this.checkItemPosition = checkItemPosition;
        notifyDataSetChanged();
    }
    public void  setSelect(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }
    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
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
        PeiTypeViewHolder holder = null;
        if (convertView == null) {
            holder = new PeiTypeViewHolder();
            convertView = inflater.inflate(R.layout.adapter_filter_item, parent, false);
            holder.tvLabel = (TextView) convertView.findViewById(R.id.tv_label);
            convertView.setTag(holder);
        } else {
            holder = (PeiTypeViewHolder) convertView.getTag();
        }


        holder.tvLabel.setText(data.get(position));
        if (position == checkItemPosition) {
            holder.tvLabel.setSelected(true);
        } else {
            holder.tvLabel.setSelected(false);
        }

        return convertView;
    }
    public class PeiTypeViewHolder {
        TextView tvLabel;
    }
}
