package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.litepal.Shop;
import com.jhcms.waimaiV3.model.Goods;

import java.util.List;

/**
 * Created by Wyj on 2017/4/5.
 */
public class ShopCarAdapter extends RecycleViewBaseAdapter {

    private OnShopItemClickListener clickListener;
    private TextView tvShopName;
    private TextView tvCommNum;
    private ImageView ivDelete;
    private RecyclerView rvShopCarItem;


    public ShopCarAdapter(Context context) {
        super(context);
    }

    public interface OnShopItemClickListener {
        void goShop(View v, int position);

        void deteleComm(View v, int position);
    }


    public void setOnShopItemClickListener(OnShopItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @Override
    public int getLayoutId() {
        return R.layout.adapter_shop_car_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {

        tvShopName = holder.getView(R.id.tv_shop_name);
        tvCommNum = holder.getView(R.id.tv_comm_num);
        ivDelete = holder.getView(R.id.iv_delete);
        rvShopCarItem = holder.getView(R.id.rv_shop_car_item);
        rvShopCarItem.setNestedScrollingEnabled(true);
        ShopCarItemAdapter itemAdapter = new ShopCarItemAdapter(mContext);

        rvShopCarItem.setAdapter(itemAdapter);
        rvShopCarItem.setLayoutManager(new GridLayoutManager(mContext, 4));
        Shop shop = (Shop) mDataList.get(position);

        final String product_info = shop.getProduct_info();
        Gson gson = new Gson();
        final List<Goods> goodses = gson.fromJson(product_info, new TypeToken<List<Goods>>() {
        }.getType());
        int count = 0;
        if (null != goodses && goodses.size() > 0) {
            for (int i = 0; i < goodses.size(); i++) {
                count += goodses.get(i).count;
            }
        }
        tvCommNum.setText("共" + count + "件");

        itemAdapter.setData(goodses);
        itemAdapter.setOnPhotoClickListener(new ShopCarItemAdapter.OnPhotoClickListener() {
            @Override
            public void PhotoClick(View view, int position) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(mContext, WaiMaiShopActivity.class);
                    intent.putExtra(WaiMaiShopActivity.SHOP_ID, goodses.get(position).shop_id);
                    intent.putExtra(WaiMaiShopActivity.PRODUCT_INFO, product_info);
                    mContext.startActivity(intent);
                }

            }
        });
        tvShopName.setText(shop.getShop_name());
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.deteleComm(v, position);
            }
        });
        tvShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.goShop(v, position);
            }
        });


    }

}
