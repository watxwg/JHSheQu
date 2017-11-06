package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.model.ShopItems;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Wyj on 2017/5/19
 * TODO:
 */
public class HotCommAdapter extends RecyclerView.Adapter<HotCommAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final String shop_id;
    private List<ShopItems.ProductsEntity> products;

    public HotCommAdapter(Context context, String shop_id) {
        this.context = context;
        this.shop_id = shop_id;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_hotcomm_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = Utils.getScreenW(context) / 4;
        params.height = Utils.getScreenW(context) / 4;
        holder.ivCommPic.setLayoutParams(params);
        holder.ivCommPic.setScaleType(ImageView.ScaleType.FIT_XY);
        Utils.LoadStrPicture(context, Api.IMAGE_URL + products.get(position).photo, holder.ivCommPic);
        holder.tvCommentPrice.setText("￥" + products.get(position).price);
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isFastDoubleClick()) {
                    Intent intent = new Intent(context, WaiMaiShopActivity.class);
                    intent.putExtra(WaiMaiShopActivity.SHOP_ID, shop_id);
                    intent.putExtra(WaiMaiShopActivity.PRODUCT_ID, products.get(position).product_id);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public void setProducts(List<ShopItems.ProductsEntity> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_comm_pic)
        ImageView ivCommPic;
        @Bind(R.id.tv_comment_price)
        TextView tvCommentPrice;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
