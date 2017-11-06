package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.SortModel;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ClassifyPopLeftAdapter extends BaseAdapter {

    Context context;
    int position;
    ArrayList<SortModel> mData;

    public ClassifyPopLeftAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<SortModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_pop_left_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        if (position == i) {
            holder.line.setVisibility(View.VISIBLE);
            holder.classifyName.setTextColor(context.getResources().getColor(R.color.themColor));
            view.setBackgroundColor(context.getResources().getColor(R.color.white));
//            holder.count.setVisibility(View.GONE);
            holder.arrowRight.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.INVISIBLE);
            holder.classifyName.setTextColor(context.getResources().getColor(R.color.selecttextcolor));
            view.setBackgroundColor(context.getResources().getColor(R.color.lineGray));
//            holder.count.setVisibility(View.VISIBLE);
            holder.arrowRight.setVisibility(View.VISIBLE);
        }
        /*if (null != mData.get(i).icon && !TextUtils.isEmpty(mData.get(i).icon)) {

        }*/
        holder.classifyName.setText(mData.get(i).title);
//        holder.count.setText("("+data.get(i).num+")");
        return view;
    }

    static class ViewHolder {
        /*@Bind(R.id.classify_img)
        ImageView classifyImg;*/
        @Bind(R.id.classify_name)
        TextView classifyName;
        @Bind(R.id.count)
        TextView count;
        @Bind(R.id.arrow_right)
        ImageView arrowRight;
        @Bind(R.id.line_staff)
        View line;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
