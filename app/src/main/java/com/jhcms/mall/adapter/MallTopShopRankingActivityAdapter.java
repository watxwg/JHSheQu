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
public class MallTopShopRankingActivityAdapter extends BaseAdapter {
    private Context context;
    private Boolean IsMallFavoritesflag=false;//是我的收藏商品适配器

    public Boolean getMallFavoritesflag() {
        return IsMallFavoritesflag;
    }

    public void setMallFavoritesflag(Boolean mallFavoritesflag) {
        IsMallFavoritesflag = mallFavoritesflag;
    }

    public MallTopShopRankingActivityAdapter(Context context) {
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
        viewhold viewhold=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_malltopshoprankingactivity_adapter,null);
            viewhold=new viewhold();
            viewhold.mTopShopImage= (ImageView) convertView.findViewById(R.id.shopImage);
            viewhold.mTvTopShopnumber= (TextView) convertView.findViewById(R.id.shopnumber_tv);
            viewhold.mTopShopName= (TextView) convertView.findViewById(R.id.Shopname);
            viewhold.mTopShopAttention= (TextView) convertView.findViewById(R.id.TopShopAttention);
            viewhold.mTopComeInshop= (TextView) convertView.findViewById(R.id.comeinshop);
            viewhold.icon1= (ImageView) convertView.findViewById(R.id.icon1);
            viewhold.icon2= (ImageView) convertView.findViewById(R.id.icon2);
            viewhold.icon3= (ImageView) convertView.findViewById(R.id.icon3);
            viewhold.icon4= (ImageView) convertView.findViewById(R.id.icon4);
            convertView.setTag(viewhold);
        }else {
            viewhold= (MallTopShopRankingActivityAdapter.viewhold) convertView.getTag();
        }
        if(IsMallFavoritesflag){
            viewhold.mTvTopShopnumber.setVisibility(View.GONE);
        }else {
            viewhold.mTopComeInshop.setCompoundDrawables(null,null,context.getResources().getDrawable(R.mipmap.icon_arrow_right),null);
            viewhold.mTvTopShopnumber.setVisibility(View.VISIBLE);
        }


        return convertView;
    }




    class  viewhold{
        private TextView mTvTopShopnumber,mTopShopName,mTopShopAttention,mTopComeInshop;
        private ImageView mTopShopImage,icon1,icon2,icon3,icon4;
    }
}
