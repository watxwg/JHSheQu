package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.model.Items;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class OrderByPopAdapter extends BaseAdapter {

    Context context;
    int p;
    List<String> nameData = new ArrayList<>();
    List<Integer> imgData = new ArrayList<>();
    int type;
    private List<Items> items;

    public OrderByPopAdapter(Context context) {
        this.context = context;
    }

    public void setType(int type) {  //  type:  1  ClassifyShopActivity ; 2  FoundActivity
        this.type = type;
    }

    public void setItems(List<Items> items) {   //  type:  1  ClassifyShopActivity ; 2  FoundActivity
        this.items = items;
        notifyDataSetChanged();
    }

    /*public void setMoneyIsEmpty(List<String> nameData, List<Integer> imgData) {
        this.nameData = nameData;
        this.imgData = imgData;
        notifyDataSetChanged();
    }*/
    public void setData(List<String> nameData) {
        this.nameData = nameData;
        notifyDataSetChanged();
    }

    public void setPosition(int p) {
        this.p = p;
    }

    public int getPosition() {
        return p;
    }
    @Override
    public int getCount() {
        if(type ==2){
            return items == null?0:items.size();
        }else {
            return nameData.size();
        }

    }

    @Override
    public Object getItem(int i) {
        if(type ==2){
            return items.get(i);
        }else {
            return nameData.get(i);
        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_choose_orderby, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        if(type ==2 ){
            holder.orderbyName.setText(items.get(i).title);

        }else {
            holder.orderbyName.setText(nameData.get(i));
        }
        if (p == i) {
            holder.selected.setVisibility(View.VISIBLE);
            holder.orderbyName.setTextColor(context.getResources().getColor(R.color.themColor));
        } else {
            holder.selected.setVisibility(View.GONE);
            holder.orderbyName.setTextColor(context.getResources().getColor(R.color.selecttextcolor));
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.orderby_name)
        TextView orderbyName;
        @Bind(R.id.selected)
        ImageView selected;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
