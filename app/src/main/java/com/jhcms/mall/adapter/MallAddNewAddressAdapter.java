package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/25.
 */
public class MallAddNewAddressAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> mlist;

    public MallAddNewAddressAdapter(Context context,ArrayList<String> mlist) {
        this.context = context;
        this.mlist=mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholder viewholder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_add_new_address_adapter,null);
            viewholder=new viewholder();
            viewholder.mAdress= (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(viewholder);
        }else {
            viewholder= (MallAddNewAddressAdapter.viewholder) convertView.getTag();
        }
        viewholder.mAdress.setText(mlist.get(position));
        return convertView;
    }

    class  viewholder{
        private TextView mAdress;
    }
}
