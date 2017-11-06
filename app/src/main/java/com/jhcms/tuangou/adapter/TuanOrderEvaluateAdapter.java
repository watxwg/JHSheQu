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
public class TuanOrderEvaluateAdapter extends BaseAdapter {
    private Context context;

    public TuanOrderEvaluateAdapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_tuan_order_evaluate_layout,null);
            viewholder=new viewholder(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder= (TuanOrderEvaluateAdapter.viewholder) convertView.getTag();
        }

        return convertView;
    }
    class viewholder{
        TextView mTvmsg;
        public  viewholder(View view){
            mTvmsg= (TextView) view.findViewById(R.id.msg);
        }
    }
}
