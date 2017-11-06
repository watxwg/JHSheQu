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
public class MallShopEventAdapter extends BaseAdapter {
    private Context context;

    public MallShopEventAdapter(Context context) {
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
        viewholde vieholde=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_shop_event_adapter,null);
            vieholde=new viewholde(convertView);
            convertView.setTag(vieholde);
        }else {
            vieholde= (viewholde) convertView.getTag();
        }
        return convertView;
    }

    class  viewholde{
        private ImageView mIvActivityStatu,mIvactivityImage;
        private TextView mActivityContent;
         public  viewholde(View view){
             mIvActivityStatu= (ImageView) view.findViewById(R.id.activitystatu_iv);
             mActivityContent= (TextView) view.findViewById(R.id.ActivityContent);
             mIvactivityImage= (ImageView) view.findViewById(R.id.ActivityImage);
         }
    }
}
