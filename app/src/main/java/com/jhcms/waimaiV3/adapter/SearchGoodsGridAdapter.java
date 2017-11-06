package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.Data_WaiMai_SearchGood;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * Date 2017/5/31.
 * TODO 外卖搜索  商品adapter。
 */

public class SearchGoodsGridAdapter extends RecyclerView.Adapter<SearchGoodsGridAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Data_WaiMai_SearchGood.ItemsEntity> data = new ArrayList<>();
    private OnGoodItemClickListener clickListener;


    public SearchGoodsGridAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_search_goods_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = Utils.getScreenW(context) / 2 - Utils.dip2px(context, 5);
        params.height = Utils.getScreenW(context) / 2 - Utils.dip2px(context, 5);
        holder.ivGoodsPic.setLayoutParams(params);

        /*商品图片*/
        Utils.LoadStrPicture(context, Api.IMAGE_URL + data.get(position).photo, holder.ivGoodsPic);

        /*商品标题*/
        holder.tvGoodsTitle.setText(data.get(position).title);

        holder.tvSales.setText(String.format(context.getString(R.string.已售), data.get(position).sales));
        /*商品价格*/
        holder.tvGoodsPices.setText("￥" + data.get(position).price);
        /*商家名称*/
        holder.tvShopName.setText(data.get(position).shop_title);
        holder.llGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shop_id
                if (null != clickListener) {
                    clickListener.onGoodItem(data.get(position).shop_id);
                }
            }
        });


    }

    public void setOnShopItemClickListener(OnGoodItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnGoodItemClickListener {
        void onGoodItem(String shop_id);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public void setData(List<Data_WaiMai_SearchGood.ItemsEntity> list) {
        this.data.clear();
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<Data_WaiMai_SearchGood.ItemsEntity> list) {
        int lastIndex = this.data.size();
        if (this.data.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_goods_pic)
        ImageView ivGoodsPic;
        @Bind(R.id.tv_goods_title)
        TextView tvGoodsTitle;
        @Bind(R.id.tv_goods_pices)
        TextView tvGoodsPices;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.tv_sales)
        TextView tvSales;
        @Bind(R.id.ll_goods)
        LinearLayout llGoods;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
