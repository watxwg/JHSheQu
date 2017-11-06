package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.waimaiV3.R;

/**
 * Created by Wyj on 2017/5/5
 * TODO:
 */
public class BusinessListViewAdapter extends RecycleViewBaseAdapter {
    private LinearLayout rlItem;
    private LinearLayout llShopInfo;
    private ImageView ivShopLogo;
    private TextView tvShopName;
    private ImageView ivLabelSon;
    private RatingBar shopStar;
    private TextView tvSaleNum;
    private TextView tvFreight;
    private TextView tvDistance;
    private TextView tvFreightTime;
    private RelativeLayout rlAllHuodong;
    private LinearLayout activeLl;
    private LinearLayout llHuodong;
    private TextView tvHuodongNum;
    private ImageView ivHuodongStatus;

    public BusinessListViewAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.adapter_business_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        rlItem = holder.getView(R.id.rl_item);
        llShopInfo = holder.getView(R.id.ll_shopInfo);
        ivShopLogo = holder.getView(R.id.iv_shop_logo);
        tvShopName = holder.getView(R.id.tv_shop_name);
        ivLabelSon = holder.getView(R.id.iv_label_son);
        shopStar = holder.getView(R.id.shop_star);
        tvSaleNum = holder.getView(R.id.tv_sale_num);
        tvFreight = holder.getView(R.id.tv_freight);
        tvDistance = holder.getView(R.id.tv_distance);
        tvFreightTime = holder.getView(R.id.tv_freight_time);
        rlAllHuodong = holder.getView(R.id.rl_all_huodong);
        activeLl = holder.getView(R.id.active_ll);
        llHuodong = holder.getView(R.id.ll_huodong);
        tvHuodongNum = holder.getView(R.id.tv_huodong_num);
        ivHuodongStatus = holder.getView(R.id.iv_huodong_status);
        activeLl.removeAllViews();
        for (int i = 0; i < 5; i++) {
            LinearLayout firstLl = new LinearLayout(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, Utils.dip2px(mContext, 10), 0, 0);
            firstLl.setLayoutParams(layoutParams);
            firstLl.setGravity(Gravity.CENTER_VERTICAL);
            firstLl.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams tv = new LinearLayout.LayoutParams(Utils.dip2px(mContext, 18), Utils.dip2px(mContext, 18));
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView tip = new ImageView(mContext);
            tip.setLayoutParams(tv);
            tip.setImageResource(R.mipmap.label_activity_shou);
            /*tip.setImageResource(R.mipmap.label_activity_jian);
            tip.setImageResource(R.mipmap.label_activity_fan);
            tip.setImageResource(R.mipmap.label_activity_fu);
            tip.setImageResource(R.mipmap.label_activity_quan);
            tip.setImageResource(R.mipmap.label_activity_tuan);
            tip.setImageResource(R.mipmap.label_activity_zhe);*/
            TextView tip2 = new TextView(mContext);
            tip2.setText("满200减50，满500减150，满1000减300");
            layout.setMargins(Utils.dip2px(mContext, 10), 0, 0, 0);
            tip2.setLayoutParams(layout);
            tip2.setTextColor(mContext.getResources().getColor(R.color.second_txt_color));
            tip2.setTextSize(12);

            firstLl.addView(tip);
            firstLl.addView(tip2);
            activeLl.addView(firstLl);
        }


    }


}
