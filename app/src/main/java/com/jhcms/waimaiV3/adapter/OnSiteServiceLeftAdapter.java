package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.model.DishModel;

/**
 * Created by Wyj on 2017/4/24.
 */
public class OnSiteServiceLeftAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private DishModel dishModel;
    private Context context;

    public OnSiteServiceLeftAdapter(Context context, DishModel dishModel) {
        this.context = context;
        this.dishModel = dishModel;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dishModel.getLeftList().size();
    }

    @Override
    public Object getItem(int position) {
        return dishModel.getLeftList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_on_site_service_left_item, null);
            holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
            holder.view = (View) convertView.findViewById(R.id.view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (dishModel.getLeftPositionSelected() == position) {
            holder.tvLeft.setSelected(true);
            holder.view.setSelected(true);
        } else {
            holder.tvLeft.setSelected(false);
            holder.view.setSelected(false);
        }
        holder.tvLeft.setText(dishModel.getLeftList().get(position).getTypeName());
        return convertView;
    }

    static class ViewHolder {
        TextView tvLeft;
        View view;
    }
}
