package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.jhcms.tuangou.activity.TuanFavorableBuyOrderDetailsActivity;
import com.jhcms.tuangou.activity.TuanOrderEvaluateActivity;

/**
 * Created by admin on 2017/5/24.
 */
public class TuanFavorableBuyOrderAdapter extends BaseAdapter {
    private Context context;

    public TuanFavorableBuyOrderAdapter(Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewholder viewholder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_tuan_favorable_buy_order_adapter,null);
            viewholder=new viewholder(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder= (TuanFavorableBuyOrderAdapter.viewholder) convertView.getTag();
        }
        switch (position%2){
            case 0:
                viewholder.mOrderStatus.setText("待评价");
                viewholder.mComePay.setText("去评价");
                break;
            case 1:
                viewholder.mOrderStatus.setText("已评价");
                viewholder.mComePay.setText("查看评价");
                break;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TuanFavorableBuyOrderDetailsActivity.class);
                intent.putExtra("flag",position%2);
                context.startActivity(intent);
            }
        });
        viewholder.mComePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position%2==0){
                    Intent intent=new Intent(context,TuanOrderEvaluateActivity.class);
                    intent.putExtra("flag",1);
                    context.startActivity(intent);
                }else if(position%2==1) {
                    Intent intent=new Intent(context,TuanOrderEvaluateActivity.class);
                    intent.putExtra("flag",2);
                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    class viewholder{
        private ImageView mShopImage;
        private TextView mShopName;
        private  TextView mOderTime;
        private  TextView mConsumemoney;
        private  TextView mTruemoney;
        private  TextView mComePay;
        private  TextView mOrderStatus;

        public viewholder(View view){
            mShopImage= (ImageView) view.findViewById(R.id.shopImage);
            mShopName= (TextView) view.findViewById(R.id.Goodname);
            mOderTime= (TextView) view.findViewById(R.id.ordertime);
            mConsumemoney= (TextView) view.findViewById(R.id.consumemoney);
            mOrderStatus= (TextView) view.findViewById(R.id.OrderStatus_tv);
            mTruemoney= (TextView) view.findViewById(R.id.goodmoney);
            mComePay= (TextView) view.findViewById(R.id.playStatu);
        }
    }
}
