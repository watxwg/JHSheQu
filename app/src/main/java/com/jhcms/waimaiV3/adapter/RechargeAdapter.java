package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.grildmodel;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/6/26.
 */
public class RechargeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<grildmodel> mdatalist;
    private  int selectpostion=-1;
    public RechargeAdapter(Context context, ArrayList<grildmodel> mdatalist) {
        this.context = context;
        this.mdatalist = mdatalist;
    }

    @Override
    public int getCount() {
        return mdatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return mdatalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getSelectpostion() {
        return selectpostion;
    }

    public void setSelectpostion(int selectpostion) {
        this.selectpostion = selectpostion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholde viewholde=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.waimai_recharge_adapter_item,null);
            viewholde=new viewholde(convertView);
            convertView.setTag(viewholde);
        }else {
            viewholde= (RechargeAdapter.viewholde) convertView.getTag();
        }
        viewholde.mTvmoeny.setText(mdatalist.get(position).getChong());
        viewholde.mTvsongmoeny.setText(mdatalist.get(position).getSong());
       if(selectpostion==position){
           viewholde.layout.setSelected(true);
       }else {
           viewholde.layout.setSelected(false);
       }
        return convertView;
    }

    class  viewholde{
        private TextView mTvmoeny;
        private  TextView mTvsongmoeny;
        private LinearLayout layout;
        public  viewholde(View view){
            mTvmoeny= (TextView) view.findViewById(R.id.moeny);
            mTvsongmoeny= (TextView) view.findViewById(R.id.songmoney);
            layout= (LinearLayout) view.findViewById(R.id.layout);
        }
    }
}
