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
public class TypeRightParentItemTwoAdapter extends BaseAdapter {
    private Context context;

    public TypeRightParentItemTwoAdapter(Context context) {
        this.context = context;
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
        viewholde viewholde=null;
        if(convertView==null){
            viewholde=new viewholde();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_grlidview_item_frgtype,null);
            viewholde.mShopimage= (ImageView) convertView.findViewById(R.id.shopImage);
            viewholde.mShopImageName= (TextView) convertView.findViewById(R.id.shopname);
            convertView.setTag(viewholde);
        }else {
            viewholde= (TypeRightParentItemTwoAdapter.viewholde) convertView.getTag();
        }
        return convertView;
    }

    class  viewholde{
        private ImageView mShopimage;
        private TextView mShopImageName;
    }
}
