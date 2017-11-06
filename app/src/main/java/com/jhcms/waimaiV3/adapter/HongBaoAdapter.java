package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.common.model.ShopHongBao;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/6/29.
 * TODO
 */

public class HongBaoAdapter extends RecyclerView.Adapter<HongBaoAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<ShopHongBao.ItemsEntity> items;

    public HongBaoAdapter(Context context, List<ShopHongBao.ItemsEntity> items) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_hongbao_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvNum.setText(items.get(position).amount);
        holder.tvTitle.setText(items.get(position).title);
        holder.tvMan.setText(String.format(context.getString(R.string.满可用), items.get(position).min_amount));
        holder.tvTime.setText(Utils.convertDate(items.get(position).dateline,""));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_man)
        TextView tvMan;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
