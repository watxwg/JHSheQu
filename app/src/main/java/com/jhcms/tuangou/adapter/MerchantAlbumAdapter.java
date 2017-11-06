package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.model.Data_Group_Shop_Album;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/4.
 * TODO
 */

public class MerchantAlbumAdapter extends RecyclerView.Adapter<MerchantAlbumAdapter.MyViewHolder> {
    private final Context context;
    private List<Data_Group_Shop_Album.ItemsBean> data;
    private OnClickListener listener;

    public MerchantAlbumAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_album_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int i) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = Utils.getScreenW(context) / 2;
        params.height = Utils.getScreenW(context) / 2;
        params.gravity = Gravity.CENTER;
        holder.framelayout.setLayoutParams(params);
        Utils.LoadStrPicture(context, Api.IMAGE_URL + data.get(i).photo, holder.ivAlbum);
        holder.tvAlbum.setText(data.get(i).title);

        holder.framelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.click(v, i);
                }
            }
        });

    }

    public interface OnClickListener {
        void click(View view, int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Data_Group_Shop_Album.ItemsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.framelayout)
        FrameLayout framelayout;
        @Bind(R.id.iv_album)
        ImageView ivAlbum;
        @Bind(R.id.tv_album)
        TextView tvAlbum;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
