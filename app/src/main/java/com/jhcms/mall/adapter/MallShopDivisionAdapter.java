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
 * Created by admin on 2017/5/5.
 */
public class MallShopDivisionAdapter extends BaseAdapter {
    private Context context;

    public MallShopDivisionAdapter(Context context) {
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
        viewholder viewholder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_shop_division_adapter,null);
            viewholder=new viewholder(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder= (MallShopDivisionAdapter.viewholder) convertView.getTag();
        }
        return convertView;
    }

    class  viewholder{
        private ImageView mShopImage;
        private TextView mShopContent;
        private  TextView mTvNewmoney;
        private  TextView mTvoldermoney;

        public  viewholder(View view){
            mShopImage= (ImageView) view.findViewById(R.id.shopimage_iv);
            mShopContent= (TextView) view.findViewById(R.id.shop_content);
            mTvNewmoney= (TextView) view.findViewById(R.id.new_money_tv);
            mTvoldermoney= (TextView) view.findViewById(R.id.older_money_tv);
        }
    }
}
