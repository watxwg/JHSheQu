package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.jhcms.common.model.Balancemodel;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/6/24.
 */
public class WaimaiBalanceActivityAdapter extends RecycleViewBaseAdapter<Balancemodel> {
    private  Context context;
    public WaimaiBalanceActivityAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.waimai_yue_adpter;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView xiaofei=holder.getView(R.id.xiaofei);
        TextView time=holder.getView(R.id.time);
        TextView moeny=holder.getView(R.id.moeny);
        Balancemodel model=   mDataList.get(position);
        xiaofei.setText(model.getIntro());
        time.setText(Utils.convertDate(Long.parseLong(model.getDateline()),null));
        if(model.getNumber().contains("-")){
           moeny.setTextColor(context.getResources().getColor(R.color.red));
            moeny.setText(model.getNumber()+"");
        }else{
            moeny.setTextColor(context.getResources().getColor(R.color.black));
            moeny.setText("+"+model.getNumber()+"");
        }

    }
}
