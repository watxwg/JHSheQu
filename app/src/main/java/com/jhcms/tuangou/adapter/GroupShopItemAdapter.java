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
import com.jhcms.common.model.Data_Group_Shop_Items;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.tuangou.CustomView.StarBar;
import com.jhcms.tuangou.activity.TuanShopAllGoodsActivity;
import com.jhcms.tuangou.activity.TuanShopDetailActivity;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/17.
 */
public class GroupShopItemAdapter extends RecyclerView.Adapter<GroupShopItemAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Data_Group_Shop_Items.ItemsBean> dataList = new ArrayList<>();

    public GroupShopItemAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_tuan_shop_frg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data_Group_Shop_Items.ItemsBean itemsBean = dataList.get(position);
        holder.bindData(itemsBean);

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setDataList(List<Data_Group_Shop_Items.ItemsBean> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<Data_Group_Shop_Items.ItemsBean> list) {
        int lastIndex = this.dataList.size();
        if (this.dataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        @Bind(R.id.tv_shop_juli)
        TextView tvShopJuli;
        @Bind(R.id.headview)
        RelativeLayout headview;
        @Bind(R.id.sonListView_item)
        ListViewForListView sonListViewItem;
        @Bind(R.id.tv_more_num)
        TextView tvMoreNum;
        @Bind(R.id.loadmorelayout)
        LinearLayout loadmorelayout;
        @Bind(R.id.tv_per)
        TextView tvPer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Data_Group_Shop_Items.ItemsBean itemsBean) {
            SonAdapter sonAdapter = null;
            Utils.LoadStrPicture(context, Api.IMAGE_URL + itemsBean.logo, ivShopLogo);
            tvShopTitle.setText(itemsBean.title);
            if (!TextUtils.isEmpty(itemsBean.have_tuan) && itemsBean.have_tuan.equals("1")) {
                tvTuan.setVisibility(View.VISIBLE);
            } else {
                tvTuan.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(itemsBean.have_maidan) && itemsBean.have_maidan.equals("1")) {
                tvMaidan.setVisibility(View.VISIBLE);
            } else {
                tvMaidan.setVisibility(View.GONE);
            }
            starBar.setStarMark(TextUtils.isEmpty(itemsBean.avg_score) ? 0 : Float.parseFloat(itemsBean.avg_score));
            tvShopScore.setText(itemsBean.avg_score + "分");
            tvShopCate.setText(itemsBean.cate_name);
            tvPer.setText(String.format(context.getString(R.string.人均), itemsBean.avg_amount));
            tvShopAddr.setText(itemsBean.addr);
            tvShopJuli.setText(itemsBean.juli_label);
            sonListViewItem.setFocusable(false);
            List<Data_Group_Home.ShopItemsEntity.ProductsEntity> products = itemsBean.products;
            sonAdapter = new SonAdapter(context, products);
            sonListViewItem.setAdapter(sonAdapter);
            if (itemsBean.total_count > 2) {
                loadmorelayout.setVisibility(View.VISIBLE);
                tvMoreNum.setText(String.format(context.getString(R.string.其他商品), itemsBean.other_count));
            } else {
                loadmorelayout.setVisibility(View.GONE);
            }
            /*点击进入更多团购*/
            loadmorelayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, TuanShopAllGoodsActivity.class);
                intent.putExtra(TuanShopAllGoodsActivity.SHOP_ID, itemsBean.shop_id);
                context.startActivity(intent);
            });
            /*点击进入店铺团购详情*/
            headview.setOnClickListener(v -> {
                Intent intent = new Intent(context, TuanShopDetailActivity.class);
                intent.putExtra(TuanShopDetailActivity.SHOP_ID, itemsBean.shop_id);
                context.startActivity(intent);
            });
        }
    }

}
