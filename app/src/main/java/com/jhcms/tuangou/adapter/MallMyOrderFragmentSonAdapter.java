package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.mall.activity.MallRefundActivity;
import com.jhcms.mall.activity.MallSalesAfterActivity;
import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/26.
 */
public class MallMyOrderFragmentSonAdapter extends BaseAdapter {
    private Context context;
    /**
     * flag where from MallMyOrderStatudDtailsActivity
     * 0 全部
     * 1代付款
     * 2 代发货
     * 3 待收货
     * 4 待评价
     */
        private int mFlagPostion=-1;

    public void setmFlagPostion(int mFlagPostion) {
        this.mFlagPostion = mFlagPostion;
    }

    public MallMyOrderFragmentSonAdapter(Context context) {
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
        viewHolder mviewholer=null;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mall_my_order_fragment_son_adapter, null);
            mviewholer=new viewHolder(convertView);
            convertView.setTag(mviewholer);
        }else {
            mviewholer= (viewHolder) convertView.getTag();
        }
        if (mFlagPostion==1){
            mviewholer.mGoodPaystatu.setVisibility(View.GONE);
        }else if(mFlagPostion==2){
            mviewholer.mGoodPaystatu.setVisibility(View.VISIBLE);
            mviewholer.mGoodPaystatu.setText("申请退款");

        }else  if(mFlagPostion==3||mFlagPostion==4){
            mviewholer.mGoodPaystatu.setVisibility(View.VISIBLE);
            mviewholer.mGoodPaystatu.setText("申请售后");
        }else {
            mviewholer.mGoodPaystatu.setVisibility(View.GONE);
        }
        mviewholer.mGoodPaystatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (mFlagPostion){
                   case 1:
                       Toast.makeText(context,"代发货",Toast.LENGTH_LONG).show();
                       break;
                   case 2:
                       Toast.makeText(context,"申请退款",Toast.LENGTH_LONG).show();
                       Intent intent2=new Intent(context,MallRefundActivity.class);
                       context.startActivity(intent2);
                       break;
                   case 3:
                       Toast.makeText(context,"申请售后",Toast.LENGTH_LONG).show();
                       Intent intent=new Intent(context, MallSalesAfterActivity.class);
                       context.startActivity(intent);
                       break;
                   case 4:
                       Toast.makeText(context,"申请售后",Toast.LENGTH_LONG).show();
                       Intent intent1=new Intent(context, MallSalesAfterActivity.class);
                       context.startActivity(intent1);
                       break;
               }
            }
        });
        return convertView;
    }

    class viewHolder{
        private ImageView mGoodImage;
        private TextView mGoodName;
        private  TextView mShopStandard;
        private  TextView mGoodMoney;
        private  TextView mGoodConunt;
        private TextView mGoodPaystatu;

        public viewHolder(View view){
            mGoodImage= (ImageView) view.findViewById(R.id.shopImage);
            mGoodName= (TextView) view.findViewById(R.id.GoodName_tv);
            mShopStandard= (TextView) view.findViewById(R.id.ShopStandard);
            mGoodMoney= (TextView) view.findViewById(R.id.Shopmoney_tv);
            mGoodConunt= (TextView) view.findViewById(R.id.good_count);
            mGoodPaystatu= (TextView) view.findViewById(R.id.paystatu);
        }
    }
}
