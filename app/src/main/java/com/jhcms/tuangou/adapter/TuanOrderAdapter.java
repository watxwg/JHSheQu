package com.jhcms.tuangou.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.model.Group_BuyOrdersFragment_Model;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.tuangou.activity.TuanOrderDetailsActivity;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/5/19.
 */
public class TuanOrderAdapter extends RecyclerView.Adapter<TuanOrderAdapter.viewhodle> {
    private Context context;
    private ArrayList<Group_BuyOrdersFragment_Model> mDataList;
    private OderstatuClickListener mOderstatuClickListener;


    public void setmOderstatuClickListener(OderstatuClickListener mOderstatuClickListener) {
        this.mOderstatuClickListener = mOderstatuClickListener;
    }

    public TuanOrderAdapter(Context context, ArrayList<Group_BuyOrdersFragment_Model> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }


    @Override
    public viewhodle onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tuan_tuan_order_adapter, parent, false);
        return new viewhodle(view);
    }

    @Override
    public void onBindViewHolder(viewhodle viewhodle, int position) {
        viewhodle.mOrderStatu.setText(mDataList.get(position).getOrder_status_label());
        Utils.LoadStrPicture(context, Api.IMAGE_URL + mDataList.get(position).getShop_logo(), viewhodle.mShopImage);
        viewhodle.mGoodName.setText(mDataList.get(position).getShop_title());
        viewhodle.mOrderTime.setText(Utils.convertDate(Long.parseLong(mDataList.get(position).getDateline()), null) + "下单");
        viewhodle.mOrderCount.setText(mDataList.get(position).getTuan_number());
        viewhodle.mOrderMoney.setText("￥" + mDataList.get(position).getTotal_price());
        if (mDataList.get(position).getOrder_status().equals("0") && mDataList.get(position).getPay_status().equals("0")) {//TODO 未支付
            //未支付状态
            viewhodle.mOrderStatu.setTextColor(context.getResources().getColor(R.color.color_yan));
            viewhodle.mOderPaystatu.setTextColor(context.getResources().getColor(R.color.color_yan));
            viewhodle.mOderPaystatu.setBackground(context.getResources().getDrawable(R.drawable.tuan_bg_white_with_yellow2_boder));
            viewhodle.mOderPaystatu.setText("去支付");
            viewhodle.CancelOrder_tv.setText("取消订单");
            viewhodle.mLayoutLinear.setVisibility(View.VISIBLE);
            viewhodle.CancelOrder_tv.setVisibility(View.VISIBLE);
            viewhodle.mOderPaystatu.setVisibility(View.VISIBLE);
            viewhodle.mOderPaystatu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("ToPay", position);//TODO 去支付
                }
            });
            viewhodle.CancelOrder_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("CancelOrder", position);//TODO 取消订单
                }
            });

        } else if (mDataList.get(position).getOrder_status().equals("5")) {//TODO 待使用的状态  待消费
            viewhodle.mOrderStatu.setTextColor(context.getResources().getColor(R.color.color_yan));
            viewhodle.mOderPaystatu.setTextColor(context.getResources().getColor(R.color.title_color));
            viewhodle.mOderPaystatu.setBackground(context.getResources().getDrawable(R.drawable.shap_bg_line_white_radius_small));
            viewhodle.mLayoutLinear.setVisibility(View.VISIBLE);
            viewhodle.CancelOrder_tv.setVisibility(View.VISIBLE);
            viewhodle.mOderPaystatu.setVisibility(View.VISIBLE);
            viewhodle.mOderPaystatu.setText("查看券码");
            viewhodle.mOderPaystatu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("LookCoupon", position);//TODO 查看券码
                }
            });
            viewhodle.CancelOrder_tv.setText("取消订单");
            viewhodle.CancelOrder_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("CancelOrder", position);//TODO 取消订单
                }
            });
        } else if (mDataList.get(position).getOrder_status().equals("8") &&
                mDataList.get(position).getComment_status().equals("0")) {//TODO 待评价
            viewhodle.mOrderStatu.setTextColor(context.getResources().getColor(R.color.title_color));
            viewhodle.mOderPaystatu.setTextColor(context.getResources().getColor(R.color.title_color));
            viewhodle.mOderPaystatu.setBackground(context.getResources().getDrawable(R.drawable.shap_bg_line_white_radius_small));
            viewhodle.mOderPaystatu.setText("查看券码");
            viewhodle.mOderPaystatu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("LookCoupon", position);//TODO 查看券码
                }
            });
            viewhodle.CancelOrder_tv.setText("去评价");
            viewhodle.CancelOrder_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("ToComment", position);//TODO 去评价
                }
            });
            viewhodle.mLayoutLinear.setVisibility(View.VISIBLE);
            viewhodle.CancelOrder_tv.setVisibility(View.VISIBLE);
            viewhodle.mOderPaystatu.setVisibility(View.VISIBLE);
        } else if (mDataList.get(position).getOrder_status().equals("8") &&
                mDataList.get(position).getComment_status().equals("1")) {//TODO 查看评价
            viewhodle.mOrderStatu.setTextColor(context.getResources().getColor(R.color.title_color));
            viewhodle.mOderPaystatu.setTextColor(context.getResources().getColor(R.color.title_color));
            viewhodle.mOderPaystatu.setBackground(context.getResources().getDrawable(R.drawable.shap_bg_line_white_radius_small));
            viewhodle.mLayoutLinear.setVisibility(View.VISIBLE);
            viewhodle.CancelOrder_tv.setVisibility(View.VISIBLE);
            viewhodle.mOderPaystatu.setText("查看评价");
            viewhodle.mOderPaystatu.setVisibility(View.VISIBLE);
            viewhodle.mOderPaystatu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("LookComment", position);//TODO 查看评价
                }
            });
            viewhodle.CancelOrder_tv.setText("查看券码");
            viewhodle.CancelOrder_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOderstatuClickListener.OderstatuClick("LookCoupon", position);//TODO 查看券码
                }
            });
        } else if (mDataList.get(position).getOrder_status().equals("-1")) {//TODO 已取消订单
            if (mDataList.get(position).getPay_status().equals("1")) {
                viewhodle.mOrderStatu.setTextColor(context.getResources().getColor(R.color.title_color));
                viewhodle.mOderPaystatu.setTextColor(context.getResources().getColor(R.color.title_color));
                viewhodle.CancelOrder_tv.setText("查看券码");
                viewhodle.CancelOrder_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOderstatuClickListener.OderstatuClick("LookCoupon", position);//TODO 查看券码
                    }
                });
                viewhodle.mOderPaystatu.setVisibility(View.GONE);
                viewhodle.mLayoutLinear.setVisibility(View.VISIBLE);
                viewhodle.CancelOrder_tv.setVisibility(View.VISIBLE);
            } else {
                viewhodle.mOrderStatu.setTextColor(context.getResources().getColor(R.color.title_color));
                viewhodle.mOderPaystatu.setTextColor(context.getResources().getColor(R.color.title_color));
                viewhodle.mOderPaystatu.setBackground(context.getResources().getDrawable(R.drawable.shap_bg_line_white_radius_small));
                viewhodle.mLayoutLinear.setVisibility(View.GONE);
                viewhodle.CancelOrder_tv.setVisibility(View.GONE);
                viewhodle.mOderPaystatu.setVisibility(View.GONE);
            }
        }

        viewhodle.Orderdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TuanOrderDetailsActivity.class);
                intent.putExtra("ORDER_ID", mDataList.get(position).getOrder_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    class viewhodle extends RecyclerView.ViewHolder {
        private RoundImageView mShopImage;
        private TextView mGoodName;
        private TextView mOrderTime;
        private TextView mOrderStatu;
        private TextView mOrderCount;
        private TextView mOrderMoney;
        private TextView CancelOrder_tv;
        private TextView mOderPaystatu;
        private LinearLayout mLayoutLinear;
        private LinearLayout Orderdetail;
        private int OrderStatuFlag = -1;//TODO 0 未支付 1  待使用的状态  待消费  2 待评价  3 查看评价  4 已取消订单


        public viewhodle(View view) {
            super(view);
            mShopImage = (RoundImageView) view.findViewById(R.id.shopImage);
            mGoodName = (TextView) view.findViewById(R.id.Goodname);

            mOrderTime = (TextView) view.findViewById(R.id.ordertime);
            mOrderStatu = (TextView) view.findViewById(R.id.OrderStatus_tv);
            mOrderCount = (TextView) view.findViewById(R.id.good_count);
            mOrderMoney = (TextView) view.findViewById(R.id.goodmoney);
            CancelOrder_tv = (TextView) view.findViewById(R.id.CancelOrder_tv);
            mOderPaystatu = (TextView) view.findViewById(R.id.playStatu);
            mLayoutLinear = (LinearLayout) view.findViewById(R.id.laybotttom);
            Orderdetail = (LinearLayout) view.findViewById(R.id.Orderdetail);

        }
    }


    public interface OderstatuClickListener {
        void OderstatuClick(String type, int postion);
    }
}
