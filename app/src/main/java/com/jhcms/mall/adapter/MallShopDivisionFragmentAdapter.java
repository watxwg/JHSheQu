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
public class MallShopDivisionFragmentAdapter extends BaseAdapter {
    private Context context;

    public MallShopDivisionFragmentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 8;
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
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mall_shop_division_frg_adapter, null);
            viewholde=new viewholde(convertView);
            convertView.setTag(viewholde);
        }else {
            viewholde= (MallShopDivisionFragmentAdapter.viewholde) convertView.getTag();
        }

        return convertView;
    }

    class  viewholde{
        private ImageView mShopImage;
        private TextView mShopName;
        public viewholde(View view){
            mShopImage= (ImageView) view.findViewById(R.id.shopimage_iv);
            mShopName= (TextView) view.findViewById(R.id.shopname_tv);
        }
    }
}
