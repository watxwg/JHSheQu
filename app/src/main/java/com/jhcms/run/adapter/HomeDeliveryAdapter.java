package com.jhcms.run.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.jhcms.common.utils.Utils;
import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.run.mode.CateInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.List;


/**
 * 作者：WangWei
 * 日期：2017/10/10 11:39
 * 描述：首页帮我送数据适配器
 */

public class HomeDeliveryAdapter extends CommonAdapter<CateInfoModel>{
    private int selectIndex;

    public HomeDeliveryAdapter(Context context, List<CateInfoModel> data) {
        super(context, data, R.layout.adapter_run_home_delivery);
        selectIndex = -1;
    }

    @Override
    public void convertViewData(CommonViewHolder holder, CateInfoModel bean) {
        int position = holder.getAdapterPosition();
        if(position==-1){
            return;
        }
        if (selectIndex==position) {
            holder.itemView.setBackgroundResource(R.drawable.bg_run_rl_select);
            holder.setVisiability(R.id.iv_label, View.VISIBLE);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.bg_run_rl_not_select);
            holder.setVisiability(R.id.iv_label, View.GONE);
        }
        Utils.LoadStrPicture(mContext,bean.getPhoto(),holder.<ImageView>getView(R.id.iv_cate_photo));
        holder.setTextViewText(R.id.tv_cate_name,bean.getTitle());
        holder.itemView.setOnClickListener(v -> {
            selectIndex=position;
            notifyDataSetChanged();
        });
    }

    /**
     * 返回选择的类型,null为没有选择
     * @return
     */
    @Nullable
    public CateInfoModel getSelectCate(){
        try{
            return mData.get(selectIndex);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
