package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.Data_BusinessListSortList;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GroupClassifyPopRightAdapter extends BaseAdapter {

    Context context;
    int position = -1;
    private List<Data_BusinessListSortList.AreaItemsBean.BusinessBean> mData;

    public GroupClassifyPopRightAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setData(List<Data_BusinessListSortList.AreaItemsBean.BusinessBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_pop_right_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        if (position == i) {
            holder.childClassifyName.setTextColor(context.getResources().getColor(R.color.themColor));
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            holder.childClassifyName.setTextColor(context.getResources().getColor(R.color.second_txt_color));
            holder.selected.setVisibility(View.GONE);
        }

        holder.childClassifyName.setText(mData.get(i).business_name);

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.child_classify_name)
        TextView childClassifyName;
        @Bind(R.id.selected)
        ImageView selected;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
