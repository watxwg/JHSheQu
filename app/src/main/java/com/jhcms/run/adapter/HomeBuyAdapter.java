package com.jhcms.run.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.OnItemClickListener;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.run.mode.CateInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 10:48
 * 描述：首页帮我买数据适配器
 */

public class HomeBuyAdapter extends CommonAdapter<CateInfoModel>{
    private OnItemClickListener<CateInfoModel> mListener;

    public HomeBuyAdapter(Context context, List<CateInfoModel> data) {
        super(context, data, R.layout.adapter_run_home_buy);
    }

    @Override
    public void convertViewData(CommonViewHolder holder, CateInfoModel bean) {
        Utils.LoadStrPicture(mContext,bean.getPhoto(),holder.<ImageView>getView(R.id.iv_photo));
        holder.setTextViewText(R.id.tv_cate_name,bean.getTitle());
        holder.setTextViewText(R.id.tv_cate_des,bean.getDesc());
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.OnItemClickListener(bean,holder.getAdapterPosition()));
        }
    }

    public void setItemClickListener(OnItemClickListener<CateInfoModel> listener){
        mListener = listener;
    }
}
