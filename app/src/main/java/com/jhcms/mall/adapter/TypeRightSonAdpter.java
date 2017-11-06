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
public class TypeRightSonAdpter extends BaseAdapter {
    private Context context;
    private  boolean ishot;
    private static final int TAG_ONLINE_ID = 1;
    public TypeRightSonAdpter(Context context, boolean ishot) {
        this.context = context;
        this.ishot = ishot;
    }

    @Override
    public int getCount() {
        return 3;
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
        Viewholde viewholdeonce;
        if(ishot){
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_typeright_son1,null);
            viewholdeonce=new Viewholde();
            viewholdeonce.mHotimage= (ImageView) convertView.findViewById(R.id.hot_iv);
            viewholdeonce.mHotnumber= (TextView) convertView.findViewById(R.id.TopNuber_tv);
            viewholdeonce.mhottitle= (TextView) convertView.findViewById(R.id.hotTitle_tv);
            viewholdeonce.mHotmoney= (TextView) convertView.findViewById(R.id.hotmoney_tv);
            viewholdeonce.mHotSoldHot= (TextView) convertView.findViewById(R.id.soldout_tv);
            viewholdeonce.mHotshopname= (TextView) convertView.findViewById(R.id.hotshopname_tv);
            convertView.setTag(R.layout.item_mall_typeright_son1,viewholdeonce);
        }else {

            viewholdeonce= (Viewholde) convertView.getTag(R.layout.item_mall_typeright_son1);
        }
        }else {
            convertView=LayoutInflater.from(context).inflate(R.layout.item_mall_frgtype_rightsonadapter_layout,null);
            Viewholdtwo viewholdtwo=new Viewholdtwo();
            viewholdtwo.mComeinshop= (TextView) convertView.findViewById(R.id.comeinshop);
            viewholdtwo.mShopattention= (TextView) convertView.findViewById(R.id.Shopattention_tv);
            viewholdtwo.mShopNumber= (TextView) convertView.findViewById(R.id.mshopnuber);
            viewholdtwo.mShopName=(TextView) convertView.findViewById(R.id.mshopname);
        }
        return convertView;
    }
    class  Viewholde{
        private TextView mHotnumber,mHotmoney,mHotSoldHot,mHotshopname,mhottitle;
        private ImageView mHotimage;
    }
    class Viewholdtwo{
        private ImageView mShopimage;
        private  TextView mShopNumber,mShopName,mShopattention,mComeinshop;
    }
}
