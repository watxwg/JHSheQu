package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.model.Data_Group_Shop_Detail;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.tuangou.activity.TuanProductDetailsActivity;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/7/19.
 * TODO
 */

public class SonRvAdapter extends RecyclerView.Adapter<SonRvAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<Data_Group_Shop_Detail.DetailBean.TuanBean.ProductBean> data = new ArrayList<>();

    public SonRvAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_tuan_frgment_listview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data_Group_Shop_Detail.DetailBean.TuanBean.ProductBean productBean = data.get(position);
        holder.bindData(productBean);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Data_Group_Shop_Detail.DetailBean.TuanBean.ProductBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }
    public void addAll(List<Data_Group_Shop_Detail.DetailBean.TuanBean.ProductBean> list) {
        int lastIndex = data.size();
        if (data.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_comm_pic)
        ImageView ivCommPic;
        @Bind(R.id.tv_comm_title)
        TextView tvCommTitle;
        @Bind(R.id.tv_comm_prices)
        TextView tvCommPrices;
        @Bind(R.id.tv_comm_oldPrices)
        TextView tvCommOldPrices;
        @Bind(R.id.tv_comm_sold)
        TextView tvCommSold;
        @Bind(R.id.rl_root)
        RelativeLayout rlRoot;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Data_Group_Shop_Detail.DetailBean.TuanBean.ProductBean productBean) {
            Utils.LoadStrPicture(context, Api.IMAGE_URL + productBean.photo, ivCommPic);
            tvCommTitle.setText(productBean.title);
            tvCommPrices.setText("￥" + productBean.price);
            tvCommOldPrices.setText("￥" + productBean.market_price);
            tvCommSold.setText("已售" + productBean.sales);
            rlRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isFastDoubleClick()) {
                        Intent intent = new Intent(context, TuanProductDetailsActivity.class);
                        intent.putExtra(TuanProductDetailsActivity.TUAN_ID, productBean.tuan_id);
                        context.startActivity(intent);
                    }

                }
            });
        }
    }

}
