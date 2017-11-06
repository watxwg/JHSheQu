package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie.
 * Date 2017/5/14
 * Time 17:35
 * TODO
 */
public class ServerTimeLeftAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> data;
    private int position;

    public ServerTimeLeftAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_on_site_service_left_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.view.setVisibility(View.GONE);
        holder.tvLeft.setText(data.get(i));
        if (position == i) {
            holder.tvLeft.setSelected(false);
        } else {
            holder.tvLeft.setSelected(true);
        }
        return view;
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.view)
        View view;
        @Bind(R.id.tv_left)
        TextView tvLeft;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
