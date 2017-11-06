package com.jhcms.mall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.activity.MallTopProductShopListActivity;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/12 09:59
 * 描述：分类推荐列表数据适配器
 */

public class CategoryRecommendAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private final List<ProductItemModel> mProductData;
    private final List<ShopItemModel> mShopData;
    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private OnEventListener listener;

    public CategoryRecommendAdapter(Context context, List<ProductItemModel> productData, List<ShopItemModel> shopData) {
        mProductData = productData;
        mShopData = shopData;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.get(R.layout.mall_list_item_category_recommend, mContext, parent);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        if (position == 0) {
            holder.<TextView>getView(R.id.tv_name).setText(R.string.mall_热销商品排行);
            if (listener != null) {
                holder.setOnClickListener(R.id.tv_more, this.listener::moreProduct);
            }
            RecyclerView rvList = holder.<RecyclerView>getView(R.id.rv_list);
            rvList.setLayoutManager(new LinearLayoutManager(mContext));
            ProductAdapter productAdapter = new ProductAdapter(mContext, mProductData, R.layout.mall_list_item_hot_product);
            rvList.setAdapter(productAdapter);
        } else if (position == 1) {
            if (listener != null) {
                holder.setOnClickListener(R.id.tv_more,this.listener::moreShop);
            }
            holder.<TextView>getView(R.id.tv_name).setText(R.string.mall_TOP店铺排行);
            RecyclerView rvList = holder.<RecyclerView>getView(R.id.rv_list);
            rvList.setLayoutManager(new LinearLayoutManager(mContext));
            ShopAdapter productAdapter = new ShopAdapter(mContext, mShopData, R.layout.mall_list_item_shop_top);
            rvList.setAdapter(productAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }



    /*---------------------------商品列表------------------------------------*/
    public class ProductAdapter extends CommonAdapter<ProductItemModel> {

        public ProductAdapter(Context context, List<ProductItemModel> data, @LayoutRes int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convertViewData(CommonViewHolder holder, ProductItemModel bean) {
            int adapterPosition = holder.getAdapterPosition();
            holder.<TextView>getView(R.id.tv_index).setText(adapterPosition + 1 + "");
            holder.<TextView>getView(R.id.tv_product_desc).setText(bean.getTitle());
            holder.<TextView>getView(R.id.tv_product_price).setText(this.mContext.getString(R.string.mall_¥) + bean.getWei_price());
            holder.<TextView>getView(R.id.tv_sale_num).setText(this.mContext.getString(R.string.mall_已售f, bean.getSales()));
            Utils.LoadStrPicture(this.mContext, Api.IMAGE_URL + bean.getPhoto(), holder.<ImageView>getView(R.id.iv_product_logo));
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(bean.getProduct_id());
                }
            });
        }
    }
    /*---------------------------商家列表------------------------------------*/
    public class ShopAdapter extends CommonAdapter<ShopItemModel> {


        public ShopAdapter(Context context, List<ShopItemModel> data, @LayoutRes int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convertViewData(CommonViewHolder holder, ShopItemModel bean) {
            int adapterPosition = holder.getAdapterPosition();
            holder.<TextView>getView(R.id.tv_index).setText(adapterPosition + 1 + "");
            holder.<TextView>getView(R.id.tv_focus_num).setText(bean.getCollects());
            holder.<FrameLayout>getView(R.id.fl_jindian).setOnClickListener((v -> {
                if (listener != null) {
                    listener.jinDian(bean);
                }
            }));
            Utils.LoadStrPicture(this.mContext, Api.IMAGE_URL + bean.getLogo(), holder.<ImageView>getView(R.id.iv_shop_logo));
            List<ShopItemModel.ProductInfo> products = bean.getRe_products();
            if (products != null && products.size() > 0) {

                ProductLogoAdapter adapter = new ProductLogoAdapter(mContext, products);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
                RecyclerView rvList = holder.<RecyclerView>getView(R.id.rv_product_logo_list);
                rvList.setLayoutManager(gridLayoutManager);
                rvList.setAdapter(adapter);
                adapter.setOnItemClickListener((productInfo,position)->{
                    if (listener != null) {
                        listener.onProductClick(productInfo.getProductIdIn());
                    }
                });
            }
        }
    }

    public void setOnEventListener(OnEventListener listener) {
        this.listener = listener;
    }

    public interface OnEventListener {
        void moreProduct(View view);

        void moreShop(View view);

        void jinDian(ShopItemModel shopItemModel);

        void onProductClick(String productId);
    }

}
