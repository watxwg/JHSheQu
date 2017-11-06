package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.util.Log;
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
 * 日期：2017/8/21 14:01
 * 描述：首页列表数据适配器
 */

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_TYPE = 20;
    private static final int NORMAL_ITEM_TYPE = 30;

    private final LayoutInflater inflater;
    private List<ProductItemModel> mData;
    private View mHeaderView;
    private Context mContext;
    private OnItemClickListener mListener;

    public HomeListAdapter(Context context
            , List<ProductItemModel> data
            ,View headerView) {
        inflater = LayoutInflater.from(context);
        mData = data;
        mHeaderView=headerView;
        mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            return new HeaderViewHolder(mHeaderView);
        } else {

            View view = inflater.inflate(R.layout.mall_list_item_home_product, parent, false);
            return new NormalViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==HEADER_TYPE){
            return;
        }else{
            ProductItemModel productItemModel = mData.get(position - 1);
            NormalViewHolder normalViewHolder= (NormalViewHolder) holder;
            int measuredWidth = normalViewHolder.ivProductLogo.getMeasuredWidth();
//            Log.e("=================", "onBindViewHolder: "+measuredWidth );
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL+productItemModel.getPhoto(),normalViewHolder.ivProductLogo);
            normalViewHolder.tvProductName.setText(productItemModel.getTitle());
            normalViewHolder.tvProductPrice.setText(mContext.getString(R.string.￥2)+productItemModel.getPrice());
            normalViewHolder.tvShopName.setText(productItemModel.getShop_title());
            normalViewHolder.tvSaleNum.setText(mContext.getString(R.string.mall_已售f,productItemModel.getSales()));
            if(mListener!=null){
                normalViewHolder.itemView.setOnClickListener(v -> mListener.onItemClick(productItemModel,position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 1 : mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER_TYPE : NORMAL_ITEM_TYPE;
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_product_logo)
        ImageView ivProductLogo;
        @Bind(R.id.tv_product_name)
        TextView tvProductName;
        @Bind(R.id.tv_product_price)
        TextView tvProductPrice;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.tv_sale_num)
        TextView tvSaleNum;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(ProductItemModel productItemModel,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
