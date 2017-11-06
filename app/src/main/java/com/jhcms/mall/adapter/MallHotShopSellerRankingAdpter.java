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
 * Created by admin on 2017/5/2.
 */
public class MallHotShopSellerRankingAdpter extends BaseAdapter {
    private Context context;
    private  boolean isonce=true;

    public MallHotShopSellerRankingAdpter(Context context, boolean isonce) {
        this.context = context;
        this.isonce = isonce;
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

            viewholde=new viewholde();
            if(convertView==null){
                if(isonce) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_mall_mallhotshopsellerrankingadpter_once, null);
                }else {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_mall_mallhotshopsellerrankingadpter_two, null);
                }
                viewholde=new viewholde();
                viewholde.mShopnumber= (TextView) convertView.findViewById(R.id.shopnumber_tv);
                viewholde.mShopgoodsname= (TextView) convertView.findViewById(R.id.shopgoodsName);
                viewholde.mShopmoney= (TextView) convertView.findViewById(R.id.Shopmoney_tv);
                viewholde.mShoapsellerout= (TextView) convertView.findViewById(R.id.ShopSellerout);
                viewholde.mShopname= (TextView) convertView.findViewById(R.id.Shopname);
                viewholde.mshopImage= (ImageView) convertView.findViewById(R.id.shopImage);
                if(!isonce){
                viewholde.Addcart= (ImageView) convertView.findViewById(R.id.Addcart);
                }
                convertView.setTag(viewholde);
            }else {
                viewholde= (MallHotShopSellerRankingAdpter.viewholde) convertView.getTag();
            }
            return  convertView;

    }

    class viewholde{
        private TextView mShopnumber,mShopgoodsname,mShopname,mShopmoney,mShoapsellerout;
        private ImageView mshopImage;
        private  ImageView Addcart;
    }
}
