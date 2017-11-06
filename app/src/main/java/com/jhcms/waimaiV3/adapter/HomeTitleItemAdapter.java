package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.IndexCate;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeTitleItemAdapter extends BaseAdapter {

    private Context context;
    private List<IndexCate> data;

    public HomeTitleItemAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<IndexCate> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_home_title, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        if (!TextUtils.isEmpty(data.get(i).thumb)) {
            if (data.get(i).thumb.startsWith("http")) {
                Utils.LoadStrPicture(context, data.get(i).thumb, holder.titleImg);
            } else {
                Utils.LoadStrPicture(context, Api.IMAGE_URL + data.get(i).thumb, holder.titleImg);
            }
        }
        holder.titleName.setText(data.get(i).title);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.title_img)
        ImageView titleImg;
        @Bind(R.id.title_name)
        TextView titleName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
