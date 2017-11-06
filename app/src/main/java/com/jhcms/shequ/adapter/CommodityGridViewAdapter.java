package com.jhcms.shequ.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.Data_Group_Shop_Goods;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.tuangou.activity.TuanProductDetailsActivity;
import com.jhcms.waimaiV3.R;

/**
 * Created by Wyj on 2017/5/4
 * TODO:首页分类--搜索--grid 的 adapter
 */
public class CommodityGridViewAdapter extends RecycleViewBaseAdapter {
    private ImageView ivCommPic;
    private TextView tvCommPices;
    private LinearLayout llComm;
    private TextView tvCommTitle;
    private TextView tvCommOldPrices;
    private TextView tvCommSold;

    public CommodityGridViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_commodity_grid_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        final Data_Group_Shop_Goods.DataBean.ItemsBean items = (Data_Group_Shop_Goods.DataBean.ItemsBean) mDataList.get(position);
        llComm = holder.getView(R.id.ll_comm);
        tvCommTitle = holder.getView(R.id.tv_comm_title);
        ivCommPic = holder.getView(R.id.iv_comm_pic);
        tvCommPices = holder.getView(R.id.tv_comm_pices);
        tvCommOldPrices = holder.getView(R.id.tv_comm_oldPrices);
        tvCommSold = holder.getView(R.id.tv_comm_sold);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = Utils.getScreenW(mContext) / 2 - Utils.dip2px(mContext, 2);
        params.height = params.width;
        ivCommPic.setLayoutParams(params);
        tvCommTitle.setText(items.title);
        Utils.LoadStrPicture(mContext, Api.IMAGE_URL + items.photo, ivCommPic);
        tvCommPices.setText("￥" + items.price);
        tvCommOldPrices.setText("￥" + items.market_price);
        tvCommSold.setText("已售" + items.sales);
        llComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(mContext, TuanProductDetailsActivity.class);
                    intent.putExtra(TuanProductDetailsActivity.TUAN_ID, items.tuan_id);
                    mContext.startActivity(intent);
                }
            }
        });


    }
}
