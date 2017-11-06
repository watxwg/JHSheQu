package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jhcms.common.model.Data_Group_Home;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/7/19.
 * TODO
 */

public class AdapterGroupAdv extends RecyclerView.Adapter<AdapterGroupAdv.AdvViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Data_Group_Home.AdvEntity> adv;

    public AdapterGroupAdv(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AdvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adv_tuangou_item, parent, false);
        return new AdvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdvViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = Utils.getScreenW(context) / 2 - Utils.dip2px(context, 1);
        params.height = Utils.getScreenW(context) / 5 - Utils.dip2px(context, 2);
        holder.iocImage.setLayoutParams(params);
        Glide.with(context)
                .load(Api.IMAGE_URL + adv.get(position).thumb)
                .placeholder(R.mipmap.home_banner_default) //占位图
                .error(R.mipmap.home_banner_default)  //出错的占位图
                .into(holder.iocImage);
        holder.llRoot.setOnClickListener(v -> {
            Utils.dealWithHomeLink(context, adv.get(position).link, null);
        });
    }


    @Override
    public int getItemCount() {
        return adv == null ? 0 : adv.size();
    }

    public void setData(List<Data_Group_Home.AdvEntity> adv) {
        this.adv = adv;
        notifyDataSetChanged();
    }

    public class AdvViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ioc_image)
        ImageView iocImage;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        public AdvViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
