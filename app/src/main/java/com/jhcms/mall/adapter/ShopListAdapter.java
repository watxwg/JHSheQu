package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.litepal.Product;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：WangWei
 * 日期：2017/8/24 17:09
 * 描述：商家列表数据适配器
 */

public class ShopListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_CONTENT_ITEM = 10;
    private static final int NORMAL_ITEM = 20;
    private LayoutInflater inflater;
    private Context mContext;
    private List<ShopItemModel> mData;
    private OnItemClickListener mListener;
    private boolean isShowRankIndex;
    private ProductProvider productProvider;

    public ShopListAdapter(Context context, List<ShopItemModel> data) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_ITEM) {

            View view = inflater.inflate(R.layout.mall_list_item_shop, parent, false);
            return new NormalViewHolder(view);
        } else   {//NO_CONTNET_TYPE
            View view = inflater.inflate(R.layout.mall_list_item_no_content, parent, false);
            return new NoContentViewHodler(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData != null && mData.size() > 0) {
            return NORMAL_ITEM;
        } else {
            return NO_CONTENT_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, int position) {
        if (getItemViewType(position) == NO_CONTENT_ITEM) {
            holder2.itemView.setBackgroundResource(R.color.mall_color_f4f4f4);
            return;
        }
        if (getItemViewType(position) == NORMAL_ITEM) {
            NormalViewHolder holder = (NormalViewHolder) holder2;
            ShopItemModel shopItemModel = mData.get(position);
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL + shopItemModel.getLogo(), holder.ivShopLogo);
            holder.tvShopName.setText(shopItemModel.getTitle());
            holder.tvFocusNum.setText(shopItemModel.getCollects());
            holder.flJindian.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.comeInShop(shopItemModel.getShop_id());
                }
            });
            List<ShopItemModel.ProductInfo> products = null;
            if (productProvider != null) {
                products = productProvider.getProducts(shopItemModel);
            } else {

                products = shopItemModel.getProducts();
            }
            if (products != null && products.size() > 0) {

                ProductLogoAdapter adapter = new ProductLogoAdapter(mContext, products);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
                holder.rvProductLogoList.setLayoutManager(gridLayoutManager);
                holder.rvProductLogoList.setAdapter(adapter);
                adapter.setOnItemClickListener((productInfo, p) -> {
                    if (mListener != null) {
                        mListener.OnProductClick(productInfo.getProductIdIn());
                    }
                });

            }
            if (isShowRankIndex) {
                holder.tvIndex.setVisibility(View.VISIBLE);
                holder.tvIndex.setText(position + 1 + "");
            } else {
                holder.tvIndex.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 是否显示排序
     *
     * @param isShowRankIndex
     */
    public void setShowRankIndex(boolean isShowRankIndex) {
        this.isShowRankIndex = isShowRankIndex;
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            return 1;
        } else {

            return mData.size();
        }
    }


    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop_logo)
        RoundImageView ivShopLogo;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.tv_focus_num)
        TextView tvFocusNum;
        @Bind(R.id.fl_jindian)
        FrameLayout flJindian;
        @Bind(R.id.rv_product_logo_list)
        RecyclerView rvProductLogoList;
        @Bind(R.id.tv_index)
        TextView tvIndex;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class NoContentViewHodler extends RecyclerView.ViewHolder {

        public NoContentViewHodler(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void comeInShop(String shopId);

        void OnProductClick(String productId);
    }

    /**
     * 提供店铺商品的接口
     */
    public interface ProductProvider {
        List<ShopItemModel.ProductInfo> getProducts(ShopItemModel shopItemModel);
    }

    public void setProductProvider(ProductProvider productProvider) {
        this.productProvider = productProvider;
    }

}
