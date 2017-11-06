package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/23.
 */
public class CanceledPopupwindowGridAdapter extends BaseAdapter {
    private Context context;
    private  int postionfalg;

    public int getPostionfalg() {
        return postionfalg;
    }

    public void setPostionfalg(int postionfalg) {
        this.postionfalg = postionfalg;
    }

    public CanceledPopupwindowGridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_canceled_popupwindow_grid_adapter,null);
            viewholder=new viewholder();
            viewholder.mtextView= (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(viewholder);
        }else {
            viewholder= (CanceledPopupwindowGridAdapter.viewholder) convertView.getTag();
        }
        if (position==postionfalg){
            viewholder.mtextView.setSelected(true);
        }else {
            viewholder.mtextView.setSelected(false);
        }
        return convertView;
    }

    class  viewholder{
        private TextView mtextView;
    }
}
