package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.model.Goods;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/4/5.
 */
public class ShopCarItemAdapter extends RecyclerView.Adapter<ShopCarItemAdapter.MyViewHolder> {
    private final Context context;
    private List<Goods> data;
    private View view;
    private OnPhotoClickListener listener;

    public ShopCarItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.adapter_shop_car_grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.width = Utils.getScreenW(context) / 4;
        layoutParams.height = Utils.getScreenW(context) / 4;
        holder.ivCommPic.setLayoutParams(layoutParams);

        Utils.LoadStrPicture(context, Api.IMAGE_URL + data.get(position).logo, holder.ivCommPic);
        holder.tvCommPices.setText("￥" + data.get(position).price);
        holder.ivCommPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.PhotoClick(v, position);
                }
            }
        });

    }

    public interface OnPhotoClickListener {
        void PhotoClick(View view, int position);
    }

    public void setOnPhotoClickListener(OnPhotoClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public void setData(List<Goods> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_comm_pic)
        ImageView ivCommPic;
        @Bind(R.id.tv_comm_pices)
        TextView tvCommPices;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
