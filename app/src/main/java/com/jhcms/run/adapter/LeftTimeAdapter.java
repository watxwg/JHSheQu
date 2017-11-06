package com.jhcms.run.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;

import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.OnItemClickListener;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.run.mode.DayConfigInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/10/10 17:36
 * 描述：
 */

public class LeftTimeAdapter extends CommonAdapter<DayConfigInfoModel> {
    private int selectIndex;
    private OnItemClickListener<DayConfigInfoModel> mListener;

    public LeftTimeAdapter(Context context, List<DayConfigInfoModel> data) {
        super(context, data, R.layout.adapter_run_left_time_item);
        selectIndex=0;
    }

    @Override
    public void convertViewData(CommonViewHolder holder, DayConfigInfoModel bean) {
        int position = holder.getAdapterPosition();
        if(position==-1){
            return;
        }
        if(position==selectIndex){
            holder.itemView.setBackgroundColor(Color.WHITE);
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor(mContext.getString(R.string.mall_color_f4f4f4)));
        }
        holder.setTextViewText(R.id.tv_content,bean.getContent());
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> {
                selectIndex=position;
                mListener.OnItemClickListener(bean,position);
                notifyDataSetChanged();
            });
        }
    }
    public void setOnItemSelectListener(OnItemClickListener<DayConfigInfoModel> listener){
        mListener = listener;
    }

    public interface ContentProvider{
        String getContent();
    }
}
