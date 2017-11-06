package com.jhcms.shequ.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.ShopLikes;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;

/**
 * Created by Wyj on 2017/5/4
 * TODO:首页分类--搜索--grid 的 adapter
 */
public class YouLikeAdapterAdapter extends RecycleViewBaseAdapter {
    private ImageView ivCommPic;
    private TextView tvCommPrices;
    private TextView tvShopName;
    private LinearLayout llComm;
    private TextView tvCommTitle;

    public YouLikeAdapterAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_you_like_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ShopLikes items = (ShopLikes) mDataList.get(position);
        llComm = holder.getView(R.id.ll_comm);
        tvCommTitle = holder.getView(R.id.tv_comm_title);
        ivCommPic = holder.getView(R.id.iv_comm_pic);
        tvCommPrices = holder.getView(R.id.tv_comm_prices);
        tvShopName = holder.getView(R.id.tv_shop_name);

        tvCommTitle.setText(items.title);
        tvCommPrices.setText("￥" + items.price);
        tvShopName.setText(items.shop_title);
        Utils.LoadStrPicture(mContext, Api.IMAGE_URL + items.photo, ivCommPic);
        llComm.setOnClickListener(v -> {
            if (!Utils.isFastDoubleClick()) {
                Intent intent = new Intent(mContext, WaiMaiShopActivity.class);
                intent.putExtra(WaiMaiShopActivity.SHOP_ID, items.shop_id);
                intent.putExtra(WaiMaiShopActivity.PRODUCT_ID, items.product_id);
                mContext.startActivity(intent);
            }
        });


    }
}
