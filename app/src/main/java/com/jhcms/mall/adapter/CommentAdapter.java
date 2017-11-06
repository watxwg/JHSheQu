package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.mall.model.CommentInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/15 14:00
 * 描述：评论列表
 */

public class CommentAdapter extends CommonAdapter<CommentInfoModel> {


    public CommentAdapter(Context context, List<CommentInfoModel> data) {
        super(context, data, R.layout.mall_list_item_comment);
    }

    @Override
    public void convertViewData(CommonViewHolder holder, CommentInfoModel bean) {

        Utils.LoadStrPicture(this.mContext, bean.getFace(), holder.<ImageView>getView(R.id.iv_customer_pic));
        String isGood = ("1".equals(bean.getIs_good())
                ?mContext.getString(R.string.mall_满意)
                :mContext.getString(R.string.mall_不满意));
        holder.<TextView>getView(R.id.tv_customer_name).setText(bean.getNickname()+"："+isGood);
        String date = Utils.convertDate(bean.getDateline(), null);
        holder.<TextView>getView(R.id.tv_time).setText(date);
        holder.<TextView>getView(R.id.tv_comment).setText(bean.getContent());
        holder.setTextViewText(R.id.tv_product_name, bean.getTitle());
        RecyclerView view = holder.<RecyclerView>getView(R.id.rv_comment_pic);
        List<String> photos = bean.getPhotos();
        if (photos == null || photos.size() == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setLayoutManager(new GridLayoutManager(this.mContext, 5));
            ImageAdapter adapter = new ImageAdapter(this.mContext,photos);
            view.setAdapter(adapter);
        }


    }

    public static class ImageAdapter extends CommonAdapter<String> {

        public ImageAdapter(Context context, List<String> data) {
            super(context, data, R.layout.mall_list_item_iamge);
        }

        @Override
        public void convertViewData(CommonViewHolder holder, String bean) {
            Utils.LoadStrPicture(this.mContext, Api.IMAGE_URL + bean, (ImageView) holder.itemView);
        }
    }

}
