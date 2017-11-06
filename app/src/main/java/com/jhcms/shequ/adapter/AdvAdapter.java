package com.jhcms.shequ.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jhcms.common.model.Adv;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/21.
 * TODO
 */

public class AdvAdapter extends RecyclerView.Adapter<AdvAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Adv> adv;

    public AdvAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.adapter_adv_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return adv == null ? 0 : adv.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0 || position == 1 || position == 2) {
            /*长宽比 28:25*/
            params.height = (Utils.getScreenW(context) * 25) / 56;
        } else {
            /*长宽比 47:25*/
            params.height = (Utils.getScreenW(context) * 25) / 94;
        }
        holder.llRoot.setLayoutParams(params);

        Utils.LoadStrPicture(context, Api.IMAGE_URL + adv.get(position).thumb, holder.ivAdv);
        holder.llRoot.setOnClickListener(v -> Utils.dealWithHomeLink(context, adv.get(position).link, null));

    }

    public void setAdv(List<Adv> adv) {
        this.adv = adv;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_adv)
        ImageView ivAdv;

        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
