package com.jhcms.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.jhcms.common.widget.ListViewForListView;
import com.jhcms.waimaiV3.R;

/**
 * Created by admin on 2017/5/6.
 */
public class MallConfirmOrderparentAdapter extends BaseAdapter {
    private Context context;
    private SelectCoupon selectCouponListener;
    public void setSelectCouponListener(SelectCoupon selectCouponListener) {
        this.selectCouponListener = selectCouponListener;
    }

    public MallConfirmOrderparentAdapter(Context context) {
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
        viewholder viewholder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_mall_confirm_order_patent_adapter,null);
            viewholder=new viewholder(convertView);
            convertView.setTag(viewholder);
        }else {
            viewholder= (MallConfirmOrderparentAdapter.viewholder) convertView.getTag();
        }
        MallConfirmOrderSonAdapter mSonAdapter=new MallConfirmOrderSonAdapter(context);
        viewholder.mSonlistview.setAdapter(mSonAdapter);
        viewholder.mSonlistview.setFocusable(false);
        viewholder.mDiscountCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCouponListener.SelectClick();
            }
        });
        return convertView;
    }

    class viewholder{
        private TextView mShopName;
        private ListViewForListView mSonlistview;
        private EditText mBuyerMessage;
        private TextView mDiscountCoupon;
        private TextView mShopCount;
        private  TextView mShopmoney;

        public viewholder(View view) {
            mShopName= (TextView) view.findViewById(R.id.tv_shop_name);
            mSonlistview= (ListViewForListView) view.findViewById(R.id.sonlistview);
            mBuyerMessage= (EditText) view.findViewById(R.id.BuyerMessage_edit);
            mDiscountCoupon= (TextView) view.findViewById(R.id.DiscountCoupon_tv);
            mShopCount= (TextView) view.findViewById(R.id.shop_count_tv);
            mShopmoney= (TextView) view.findViewById(R.id.mShopmoney_tv);

        }
    }

    public  interface  SelectCoupon{
        void  SelectClick();
    }
}
