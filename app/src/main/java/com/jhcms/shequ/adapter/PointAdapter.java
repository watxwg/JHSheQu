package com.jhcms.shequ.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class PointAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<PoiItem> poiItems;// poi数据


    public PointAdapter(Context context){
        super();
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    public void setItem(List<PoiItem> poiItems) {
        this.poiItems = poiItems;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return poiItems == null ? 0 : poiItems.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return poiItems.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder holder = null;
        if (arg1 == null) {
            holder = new ViewHolder();
            arg1 = mInflater.inflate(R.layout.adapter_addritem, null);
            holder.mTitle = (TextView)arg1.findViewById(R.id.title);
            holder.mAdderss = (TextView)arg1.findViewById(R.id.context);
            holder.mPic = (ImageView)arg1.findViewById(R.id.logo_image);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        PoiItem Item = poiItems.get(arg0);
//		String str = Item.getTitle();
//		String str1 = Item.getSnippet();
        holder.mTitle.setText(Item.getTitle());
        holder.mAdderss.setText(Item.getSnippet());
        return arg1;


    }

    private final class ViewHolder {
        private TextView mTitle,mAdderss;
        private ImageView mPic;
    }
}