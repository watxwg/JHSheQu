package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.Group_OfferToPay_model;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.tuangou.activity.GroupOfferToPayOrderDeatail;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/25.
 */
public class GroupOfferToPayAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Group_OfferToPay_model> mDataList;

    public GroupOfferToPayAdapter(Context context, ArrayList<Group_OfferToPay_model> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }
    private  BtnOnclick btnOnclick;

    public BtnOnclick getBtnOnclick() {
        return btnOnclick;
    }

    public void setBtnOnclick(BtnOnclick btnOnclick) {
        this.btnOnclick = btnOnclick;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tuan_hroup_offer_adapter, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Utils.LoadUrlImage(context, Api.IMAGE_URL+mDataList.get(position).getShop_logo(),viewHolder.shopImage);
        viewHolder.Goodname.setText(mDataList.get(position).getShop_title());
        viewHolder.ordertime.setText(Utils.convertDate(Long.parseLong(mDataList.get(position).getDateline()),null)+"下单");
        viewHolder.goodCount.setText("￥"+mDataList.get(position).getTotal_price());
        viewHolder.goodmoney.setText("￥"+mDataList.get(position).getAmount());
        if(mDataList.get(position).getOrder_status().equals("-1")){//取消订单
            viewHolder.laybotttom.setVisibility(View.GONE);
            viewHolder.OrderStatusTv.setText("已取消");
            viewHolder.OrderStatusTv.setTextColor(context.getResources().getColor(R.color.color_yan));
        }else if(mDataList.get(position).getOrder_status().equals("0")){//未付款
            viewHolder.OrderStatusTv.setText("待支付");
            viewHolder.OrderStatusTv.setTextColor(context.getResources().getColor(R.color.color_yan));
            viewHolder.laybotttom.setVisibility(View.VISIBLE);
            viewHolder.playStatu.setVisibility(View.VISIBLE);
            viewHolder.CancelOrderTv.setVisibility(View.GONE);
            viewHolder.playStatu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOnclick.BtnClick("toplay",position);
                }
            });
        }else if (mDataList.get(position).getOrder_status().equals("8")&&mDataList.get(position).getComment_status().equals("0")){//去评价
            viewHolder.OrderStatusTv.setText("待评价");
            viewHolder.OrderStatusTv.setTextColor(context.getResources().getColor(R.color.title_color));
            viewHolder.laybotttom.setVisibility(View.VISIBLE);
            viewHolder.playStatu.setVisibility(View.GONE);
            viewHolder.CancelOrderTv.setVisibility(View.VISIBLE);
            viewHolder.CancelOrderTv.setText("去评价");
            viewHolder.CancelOrderTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOnclick.BtnClick("ToComment",position);
                }
            });

        }else if(mDataList.get(position).getOrder_status().equals("8")&&mDataList.get(position).getComment_status().equals("1")){
            viewHolder.OrderStatusTv.setText("已评价");
            viewHolder.OrderStatusTv.setTextColor(context.getResources().getColor(R.color.title_color));
            viewHolder.laybotttom.setVisibility(View.VISIBLE);
            viewHolder.playStatu.setVisibility(View.GONE);
            viewHolder.CancelOrderTv.setVisibility(View.VISIBLE);
            viewHolder.CancelOrderTv.setText("查看评价");
            viewHolder.CancelOrderTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOnclick.BtnClick("LookComment",position);
                }
            });
        }
        viewHolder.Orderdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GroupOfferToPayOrderDeatail.class);
                intent.putExtra("ORDER_ID",mDataList.get(position).getOrder_id());
                context.startActivity(intent);
            }
        });

        return convertView;
    }


     class ViewHolder {
        @Bind(R.id.shopImage)
        RoundImageView shopImage;
        @Bind(R.id.Goodname)
        TextView Goodname;
        @Bind(R.id.ordertime)
        TextView ordertime;
        @Bind(R.id.OrderStatus_tv)
        TextView OrderStatusTv;
        @Bind(R.id.good_count)
        TextView goodCount;
        @Bind(R.id.goodmoney)
        TextView goodmoney;
        @Bind(R.id.Orderdetail)
        LinearLayout Orderdetail;
        @Bind(R.id.CancelOrder_tv)
        TextView CancelOrderTv;
        @Bind(R.id.playStatu)
        TextView playStatu;
        @Bind(R.id.laybotttom)
        LinearLayout laybotttom;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public  interface  BtnOnclick{
        void BtnClick(String type, int postion);
    }
}
