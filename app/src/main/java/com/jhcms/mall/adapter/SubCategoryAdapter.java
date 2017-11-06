package com.jhcms.mall.adapter;

import android.content.Context;
import android.icu.util.ULocale;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.AccountPicker;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.mall.model.CategoryModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/18 15:16
 * 描述：分类adapter
 */

public class SubCategoryAdapter extends CommonAdapter<CategoryModel> {
    private OnItemClickListener<CategoryModel> mListener;

    public SubCategoryAdapter(Context context, List<CategoryModel> data) {
        super(context, data, R.layout.mall_list_item_sub_category);
    }

    @Override
    public void convertViewData(CommonViewHolder holder, CategoryModel bean) {
        holder.setTextViewText(R.id.tv_name, bean.getTitle());
        List<CategoryModel> childrens = bean.getChildrens();
        if (childrens != null && childrens.size() > 0) {
            RecyclerView view = holder.<RecyclerView>getView(R.id.rv_list);
            view.setVisibility(View.VISIBLE);
            holder.getView(R.id.v_devider).setVisibility(View.VISIBLE);
            CateGoryDetailAdapter adapter = new CateGoryDetailAdapter(mContext, childrens);
            view.setLayoutManager(new GridLayoutManager(mContext, 3));
            view.setAdapter(adapter);
        } else {
            holder.getView(R.id.rv_list).setVisibility(View.GONE);
            holder.getView(R.id.v_devider).setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<CategoryModel> listener) {
        mListener = listener;
    }

    /**
     * 详细分类的adapter
     */
    public class CateGoryDetailAdapter extends CommonAdapter<CategoryModel> {

        public CateGoryDetailAdapter(Context context, List<CategoryModel> data) {
            super(context, data, R.layout.mall_list_item_category_detail);
        }

        @Override
        public void convertViewData(CommonViewHolder holder, CategoryModel bean) {
            holder.setTextViewText(R.id.tv_cate_name, bean.getTitle());
            Utils.LoadStrPicture(this.mContext, Api.IMAGE_URL + bean.getPhoto(), holder.<ImageView>getView(R.id.iv_category));
            int adapterPosition = holder.getAdapterPosition();
            holder.itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.OnItemClickListener(bean, adapterPosition);
                }
            });
        }
    }
}
