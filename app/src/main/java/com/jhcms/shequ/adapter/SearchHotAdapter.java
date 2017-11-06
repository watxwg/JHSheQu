package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/4/8.
 */
public class SearchHotAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    public SearchHotAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private List<String> mListdata;

    public void setmListdata(List<String> mListdata) {
        this.mListdata = mListdata;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListdata == null ? 0 : mListdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mListdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_hot_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvHotTitle.setText(mListdata.get(position));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_hot_title)
        TextView tvHotTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
