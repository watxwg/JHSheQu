package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.jhcms.common.model.Data_Group_Home;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.tuangou.CustomView.StarBar;
import com.jhcms.tuangou.activity.TuanShopAllGoodsActivity;
import com.jhcms.tuangou.activity.TuanShopDetailActivity;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/17.
 */
public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.MyViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<Data_Group_Home.ShopItemsEntity> list;
    private Context context;

    public ParentAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.item_tuan_shop_frg, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Data_Group_Home.ShopItemsEntity shopItemsEntity = list.get(position);
        Utils.LoadStrPicture(context, Api.IMAGE_URL + shopItemsEntity.logo, viewHolder.ivShopLogo);
        viewHolder.tvShopTitle.setText(shopItemsEntity.title);
        if (shopItemsEntity.have_tuan.equals("1")) {
            viewHolder.tvTuan.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvTuan.setVisibility(View.GONE);
        }
        if (shopItemsEntity.have_maidan.equals("1")) {
            viewHolder.tvMaidan.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvMaidan.setVisibility(View.GONE);
        }
        viewHolder.starBar.setStarMark(TextUtils.isEmpty(shopItemsEntity.avg_score) ? 0 : Float.parseFloat(shopItemsEntity.avg_score));
        viewHolder.tvShopScore.setText(shopItemsEntity.avg_score + "分");
        viewHolder.tvShopCate.setText(shopItemsEntity.cate_name);
        viewHolder.tvPer.setText(String.format(context.getString(R.string.人均), shopItemsEntity.avg_amount));
        viewHolder.tvShopAddr.setText(shopItemsEntity.addr);
        viewHolder.tvShopJuli.setText(shopItemsEntity.juli_label);

        List<Data_Group_Home.ShopItemsEntity.ProductsEntity> products = shopItemsEntity.products;
        viewHolder.sonListViewItem.setFocusable(false);
        SonAdapter sonAdapter = new SonAdapter(context, products);
        viewHolder.sonListViewItem.setAdapter(sonAdapter);
        if (shopItemsEntity.total_count > 2) {
            viewHolder.loadmorelayout.setVisibility(View.VISIBLE);
            viewHolder.tvMoreNum.setText(String.format(context.getString(R.string.其他商品), shopItemsEntity.other_count));
        } else {
            viewHolder.loadmorelayout.setVisibility(View.GONE);
        }
        /*点击进入更多团购*/
        viewHolder.loadmorelayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, TuanShopAllGoodsActivity.class);
            intent.putExtra(TuanShopAllGoodsActivity.SHOP_ID, shopItemsEntity.shop_id);
            context.startActivity(intent);
        });
        /*点击进入店铺团购详情*/
        viewHolder.headview.setOnClickListener(v -> {
            Intent intent = new Intent(context, TuanShopDetailActivity.class);
            intent.putExtra(TuanShopDetailActivity.SHOP_ID, shopItemsEntity.shop_id);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setData(List<Data_Group_Home.ShopItemsEntity> list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop_logo)
        ImageView ivShopLogo;
        @Bind(R.id.tv_shop_title)
        TextView tvShopTitle;
        @Bind(R.id.tv_tuan)
        SuperTextView tvTuan;
        @Bind(R.id.tv_maidan)
        SuperTextView tvMaidan;
        @Bind(R.id.starBar)
        StarBar starBar;
        @Bind(R.id.tv_shop_score)
        TextView tvShopScore;
        @Bind(R.id.tv_shop_cate)
        TextView tvShopCate;
        @Bind(R.id.tv_shop_addr)
        TextView tvShopAddr;
        @Bind(R.id.headview)
        RelativeLayout headview;
        @Bind(R.id.sonListView_item)
        ListViewForListView sonListViewItem;
        @Bind(R.id.tv_more_num)
        TextView tvMoreNum;
        @Bind(R.id.tv_per)
        TextView tvPer;
        @Bind(R.id.tv_shop_juli)
        TextView tvShopJuli;
        @Bind(R.id.loadmorelayout)
        LinearLayout loadmorelayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
