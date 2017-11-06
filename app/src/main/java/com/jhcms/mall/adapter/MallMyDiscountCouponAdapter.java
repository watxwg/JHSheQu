package com.jhcms.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/25.
 */
public class MallMyDiscountCouponAdapter extends BaseAdapter {
    private Context context;
    private int mFlagpostion;

    public MallMyDiscountCouponAdapter(Context context, int mFlagpostion) {
        this.context = context;
        this.mFlagpostion = mFlagpostion;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewholder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.mall_my_discount_coupon_adapter,null);
            mViewholder=new ViewHolder(convertView);
            convertView.setTag(mViewholder);
        }else {
            mViewholder= (ViewHolder) convertView.getTag();
        }
        if(mFlagpostion==0){//可用
            mViewholder.mShopMoney.setTextColor(Color.parseColor("#FF6600"));
            mViewholder.mImmediateUse.setBackground(context.getResources().getDrawable(R.drawable.mall_red_bag));
            mViewholder.mImmediateUse.setText("立即使用");
        }else {
            mViewholder.mShopMoney.setTextColor(context.getResources().getColor(R.color.second_txt_color));
            mViewholder.mImmediateUse.setBackground(context.getResources().getDrawable(R.drawable.shap_bg_gray_radius));
            mViewholder.mImmediateUse.setText("已失效");
        }
        return convertView;
    }

    class  ViewHolder{
        private ImageView mShopImage;
        private TextView mShopName;
        private  TextView mShopMoney;
        private TextView mShopMoneyExplanation;//活动说明
        private  TextView mShopTime;
        private  TextView mImmediateUse;
        public  ViewHolder(View view){
            mShopImage= (ImageView) view.findViewById(R.id.shopImage);
            mShopName= (TextView) view.findViewById(R.id.shopname);
            mShopMoney= (TextView) view.findViewById(R.id.shopMoney);
            mShopMoneyExplanation= (TextView) view.findViewById(R.id.shopmoneyexplanation);
            mShopTime= (TextView) view.findViewById(R.id.ShopTime);
            mImmediateUse= (TextView) view.findViewById(R.id.ImmediateUse);

        }

    }
}
