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
 * Created by admin on 2017/5/18.
 * 会员卡适配器
 */
public class MallMembershipCardAdapter extends BaseAdapter {
    private Context context;

    public MallMembershipCardAdapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_member_ship_card_adapter_layout,null);
            viewholder=new viewholder(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder= (MallMembershipCardAdapter.viewholder) convertView.getTag();
        }
        return convertView;
    }

    class  viewholder{
        private ImageView mShopImageHead;
        private TextView mShopTilte;
        private  TextView mShopCartMagess;

        public viewholder(View view) {
            mShopImageHead= (ImageView) view.findViewById(R.id.shopImageHead);
            mShopTilte= (TextView) view.findViewById(R.id.mshopTitle);
            mShopCartMagess= (TextView) view.findViewById(R.id.shopcarmesage_tv);
        }
    }
}
