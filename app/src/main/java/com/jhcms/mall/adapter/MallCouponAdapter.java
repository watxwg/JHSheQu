package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/4.
 */
public class MallCouponAdapter extends BaseAdapter {
    private Context context;

    public MallCouponAdapter(Context context) {
        this.context = context;
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
        viewholde viewholde=null;
        if(viewholde==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_coupon_adapter,null);
            viewholde=new viewholde();
            viewholde.mShopImage= (ImageView) convertView.findViewById(R.id.Shop_iv);
            viewholde.mShopName= (TextView) convertView.findViewById(R.id.ShopName_tv);
            viewholde.mShopMoney= (TextView) convertView.findViewById(R.id.Shopmoney_tv);
            viewholde.mShopMoneyContent= (TextView) convertView.findViewById(R.id.shopmoneycontent_tv);
            viewholde.MshopTime= (TextView) convertView.findViewById(R.id.mShoptime);
            viewholde.mShopGet= (TextView) convertView.findViewById(R.id.shopGet);
            convertView.setTag(viewholde);
        }else {
            viewholde= (MallCouponAdapter.viewholde) convertView.getTag();
        }
        return convertView;
    }
    class  viewholde{
        private ImageView mShopImage;
        private TextView mShopName,mShopMoney,mShopMoneyContent,MshopTime,mShopGet;
    }
}
