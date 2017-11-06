package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.common.model.YouHuiModel;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/6/15.
 */
public class OrderDetailsActivityYouHuiAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    ArrayList<YouHuiModel> youhui;

    public OrderDetailsActivityYouHuiAdapter(Context context, ArrayList<YouHuiModel> youhui) {
        this.context = context;
        this.youhui = youhui;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return youhui.size();
    }

    @Override
    public Object getItem(int position) {
        return youhui.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.waimai_order_details_activity_adpater,null);
            holder=new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (MyViewHolder) convertView.getTag();
        }
        holder.mTvJian.setText(youhui.get(position).getWord());
        holder.mTvJian.setSolid(Color.parseColor("#"+youhui.get(position).getColor()));
        holder.mTvJianInfo.setText(youhui.get(position).getTitle());
        holder.mTvjianPrice.setText("-￥"+youhui.get(position).getAmount());
        return convertView;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_jian)
        SuperTextView mTvJian;
        @Bind(R.id.tv_jianinfo)
        TextView mTvJianInfo;
        @Bind(R.id.tv_jianprices)
        TextView mTvjianPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
