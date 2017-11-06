package com.jhcms.shequ.adapter;

import android.content.Context;
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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/23.
 */
public class ModuleAdapter extends BaseAdapter {

    private Context context;
    private List<IndexCate> data;

    public ModuleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_module, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTitleTv.setText(data.get(i).title);
        Utils.LoadStrPicture(context, Api.IMAGE_URL + data.get(i).thumb, holder.mPicIv);
        return view;
    }

    public void setData(List<IndexCate> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.title_img)
        ImageView mPicIv;
        @Bind(R.id.title_name)
        TextView mTitleTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
