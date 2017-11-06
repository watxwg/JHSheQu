package com.jhcms.mall.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.mall.model.interfaces.ProductInfoInterface;
import com.jhcms.waimaiV3.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：WangWei
 * 日期：2017/8/24 17:27
 * 描述：商家列表中的产品列表数据适配器
 */

public class ProductLogoAdapter extends RecyclerView.Adapter<ProductLogoAdapter.NormalViewHolder>{
    private LayoutInflater inflater;
    private Context mContext;
    private List<? extends ProductInfoInterface> mData;
    private OnItemClickListener<ProductInfoInterface> mListener;
//    private ShopListAdapter.OnItemClickListener mListener;

    public ProductLogoAdapter(Context context, List<? extends ProductInfoInterface> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
    }
    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mall_list_item_product_logo, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        ProductInfoInterface productInfoInterface = mData.get(position);
        Utils.LoadStrPicture(mContext, Api.IMAGE_URL+productInfoInterface.getPhotoIn(),holder.ivProductLogo);
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.OnItemClickListener(productInfoInterface,position);
            }
        });
    }
    public void setOnItemClickListener(OnItemClickListener<ProductInfoInterface> listener){
        mListener= listener;
    }
    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_product_logo)
        ImageView ivProductLogo;
        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
