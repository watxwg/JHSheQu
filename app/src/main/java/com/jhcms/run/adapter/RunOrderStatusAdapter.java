package com.jhcms.run.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * on 2017/10/10.15:40
 * TODO
 */

public class RunOrderStatusAdapter extends RecyclerView.Adapter<RunOrderStatusAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> data;

    public RunOrderStatusAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_order_status_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvStatus.setText(data.get(position));
        holder.tvStatus.setSelected(false);
        if (position == 0) {
            holder.viewLeft.setVisibility(View.GONE);
        } else if (position == data.size() - 1) {
            holder.viewRight.setVisibility(View.GONE);
            holder.tvStatus.setSelected(true);
        } else {
            holder.viewLeft.setVisibility(View.VISIBLE);
            holder.viewRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_status)
        TextView tvStatus;
        @Bind(R.id.view_right)
        View viewRight;
        @Bind(R.id.iv_status)
        ImageView ivStatus;
        @Bind(R.id.view_left)
        View viewLeft;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
