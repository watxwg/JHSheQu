package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 作者：WangWei
 * 日期：2017/8/24 15:43
 * 描述：双列商品列表数据适配器
 */

public class ProductDoubleAdapter extends RecyclerView.Adapter<ProductDoubleAdapter.NormalViewHolder> {


    private LayoutInflater inflater;
    private List<ProductItemModel> mData;
    private Context mContext;
    private boolean isShowRankIndex = false;
    private OnItemClickListener<ProductItemModel> mListener;

    public ProductDoubleAdapter(Context context, List<ProductItemModel> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mall_list_item_product_double, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        ProductItemModel productItemModel = mData.get(position);
        Utils.LoadStrPicture(mContext, Api.IMAGE_URL + productItemModel.getPhoto(), holder.ivProductLogo);
        holder.tvProductDesc.setText(productItemModel.getTitle());
        holder.tvProductPrice.setText(mContext.getString(R.string.￥2) + productItemModel.getWei_price());
        holder.tvSaleNum.setText(mContext.getString(R.string.mall_已售f,productItemModel.getSales()));
        if(isShowRankIndex){
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(position+1+"");
        }else{
            holder.tvIndex.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.OnItemClickListener(productItemModel,position);
            }
        });
    }

    public void addData(List<ProductItemModel> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
    /**
     * 是否显示排序
     * @param isShowRankIndex
     */
    public void setShowRankIndex(boolean isShowRankIndex){
        this.isShowRankIndex = isShowRankIndex;
    }
    public void setOnItemClickListener(OnItemClickListener<ProductItemModel> listener){
        mListener = listener;
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_product_logo)
        ImageView ivProductLogo;
        @Bind(R.id.tv_product_desc)
        TextView tvProductDesc;
        @Bind(R.id.tv_product_price)
        TextView tvProductPrice;
        @Bind(R.id.tv_sale_num)
        TextView tvSaleNum;
        @Bind(R.id.tv_index)
        TextView tvIndex;
        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
