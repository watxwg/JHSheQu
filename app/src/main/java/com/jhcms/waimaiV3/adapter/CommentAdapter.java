package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jhcms.common.model.ShopComment;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.PicturePreviewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by wangyujiew .
 * Date 2017/5/9
 * Time 20:45
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<ShopComment> dataList = new ArrayList<>();


    public CommentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_comment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ShopComment items = dataList.get(position);
        /*头像*/
        Utils.LoadStrPicture(context, Api.IMAGE_URL + items.face, holder.ivCommentHead);
        /*用户名*/
        holder.tvCommentName.setText(items.nickname);
        /*评分*/

        holder.rbComment.setProgress(((int) (10 * Double.parseDouble(items.score))));
        /*送达时间*/
        holder.tvCommentDelivery.setText(items.pei_time);
        /*评价时间*/
        holder.tvCommentTime.setText(Utils.convertDate(items.dateline, null));
        /*评价内容*/
        holder.tvComment.setText(items.content);

        /*评价图片*/
        holder.rvShopCarItem.setNestedScrollingEnabled(false);
        CommentItemAdapter itemAdapter = new CommentItemAdapter(context);
        holder.rvShopCarItem.setAdapter(itemAdapter);
        holder.rvShopCarItem.setLayoutManager(new GridLayoutManager(context, 4));
        /*如果评论图片返回数据不为空且大于0*/
        if (null != items.comment_photos && items.comment_photos.size() > 0) {
            holder.rvShopCarItem.setVisibility(View.VISIBLE);
            itemAdapter.setData(items.comment_photos);
        } else {
            holder.rvShopCarItem.setVisibility(View.GONE);
        }
        final ArrayList<String> imageList = new ArrayList<>();
        for (int i = 0; i < items.comment_photos.size(); i++) {
            imageList.add(items.comment_photos.get(i).photo);
        }
        itemAdapter.setOnPhotoClickListener(new CommentItemAdapter.OnPhotoClickListener() {
            @Override
            public void PhotoClick(View view, int pos) {
                Intent intent = new Intent(context, PicturePreviewActivity.class);
                intent.putExtra(PicturePreviewActivity.POSITION, pos);
                intent.putStringArrayListExtra(PicturePreviewActivity.IMAGELIST, imageList);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,
                        view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(context, intent,
                        compat.toBundle());
            }
        });
        /*商家回复*/
        if (!TextUtils.isEmpty(items.reply)) {
            holder.tvReplyComment.setVisibility(View.VISIBLE);
            holder.tvReplyComment.setText("商家回复:" + items.reply);
        } else {
            holder.tvReplyComment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setDataList(List<ShopComment> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<ShopComment> list) {
        int lastIndex = this.dataList.size();
        if (this.dataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_comment_head)
        RoundImageView ivCommentHead;
        @Bind(R.id.tv_comment_name)
        TextView tvCommentName;
        @Bind(R.id.tv_comment_time)
        TextView tvCommentTime;
        @Bind(R.id.rb_comment)
        RatingBar rbComment;
        @Bind(R.id.tv_comment_delivery)
        TextView tvCommentDelivery;
        @Bind(R.id.tv_comment)
        TextView tvComment;
        @Bind(R.id.rv_shop_car_item)
        RecyclerView rvShopCarItem;
        @Bind(R.id.tv_reply_comment)
        TextView tvReplyComment;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

