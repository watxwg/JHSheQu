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
 * Created by admin on 2017/5/8.
 */
public class MallConfirmOrderSonAdapter extends BaseAdapter {
    private Context context;

    public MallConfirmOrderSonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
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
        ChildHolder childHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_confirm_order_son_adapter,null);
            childHolder=new ChildHolder();
            childHolder.tv_product_name= (TextView) convertView.findViewById(R.id.tv_intro);
            childHolder.iv_decrease= (TextView) convertView.findViewById(R.id.desc);
            childHolder.tv_price= (TextView) convertView.findViewById(R.id.price);
            childHolder.tv_count= (TextView) convertView.findViewById(R.id.shopcount);
            childHolder.mShopimage= (ImageView) convertView.findViewById(R.id.iv_adapter_list_pic);
            convertView.setTag(childHolder);
        }else {
            childHolder= (ChildHolder) convertView.getTag();
        }
        return convertView;
    }

    /**
     * 子元素绑定器
     *
     *
     */
    private class ChildHolder
    {
        TextView tv_product_name;
        TextView tv_price;
        ImageView mShopimage;
        TextView tv_count;
        TextView iv_decrease;
        private  TextView mDeleteView;
    }
}
