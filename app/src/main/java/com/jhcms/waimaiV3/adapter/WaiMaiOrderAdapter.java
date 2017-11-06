package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jhcms.common.model.ShopOrderModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.waimaiV3.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * Created by Wyj on 2017/4/17.
 */
public class WaiMaiOrderAdapter extends RecyclerView.Adapter<WaiMaiOrderAdapter.MyViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private OnClickListener listener;
    private List<ShopOrderModel> dataList = new ArrayList<>();

    public WaiMaiOrderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<ShopOrderModel> data) {
        dataList.clear();
        dataList.addAll(data);
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_waimai_order_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //Utils.LoadStrPicture(context, Api.IMAGE_URL + dataList.get(position).shop_logo, holder.ivShopHead);
        Glide.with(context).load(Api.IMAGE_URL+dataList.get(position).shop_logo).into(holder.ivShopHead);
        holder.tvShopName.setText(dataList.get(position).shop_title);
        holder.tvOrderTime.setText(Utils.convertDate(Long.parseLong(dataList.get(position).dateline),null));
        holder.orderCommodity.setText(dataList.get(position).product_title);
        holder.tvOrderPrice.setText("￥"+(Float.parseFloat(dataList.get(position).amount)+Float.parseFloat(dataList.get(position).money)));
        holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.title_color));
        holder.tvOrderStatus.setText(dataList.get(position).msg);
        if (Integer.parseInt(dataList.get(position).pay_status) == 1) {//已付款
            holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.title_color));
            holder.tvRight.setTextColor(context.getResources().getColor(R.color.title_color));
            holder.tvRight.setBackgroundResource(R.drawable.shap_bg_line_white_radius);
            if (dataList.get(position).refund.equals("-1")) {//正常单
                switch (Integer.parseInt(dataList.get(position).order_status)) {
                    case -1://已取消
                        holder.tvRight.setText("再来一单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.GONE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 0://等待商户接单
                        holder.tvRight.setText("再来一单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });

                        holder.tvLeft.setText("取消订单");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("CancleOrder",position);
                            }
                        });
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 1://商户已结单 预订单
                        holder.tvRight.setText("催单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("reminder",position);
                            }
                        });
                        holder.mtvLeft1.setText("确认送达");
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.tvLeft.setText("申请退款");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("refundorder",position);
                            }
                        });
                        if (!dataList.get(position).getPei_type().equals("3")) {
                            if (Long.parseLong(dataList.get(position).cui_time) > 0) {
                                holder.tvRight.setVisibility(View.GONE);
                            } else {
                                holder.tvRight.setVisibility(View.VISIBLE);
                            }
                            if(dataList.get(position).getPei_type().equals("1")){
                                holder.mtvLeft1.setVisibility(View.GONE);
                            }else {
                                holder.mtvLeft1.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.tvRight.setVisibility(View.GONE);
                            holder.mtvLeft1.setText("确认自提");
                            holder.mtvLeft1.setVisibility(View.VISIBLE);
                        }

                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 2://商家已接单
                        holder.tvRight.setText("催单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("reminder",position);
                            }
                        });
                        holder.mtvLeft1.setText("确认送达");
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.tvLeft.setText("申请退款");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("refundorder",position);
                            }
                        });
                        if(!dataList.get(position).getPei_type().equals("3")) {
                            if (Long.parseLong(dataList.get(position).cui_time) > 0) {
                                holder.tvRight.setVisibility(View.GONE);
                            } else {
                                holder.tvRight.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.tvRight.setVisibility(View.GONE);
                            holder.mtvLeft1.setText("确认自提");
                        }
                        if(!dataList.get(position).getPei_type().equals("3")){
                            if(dataList.get(position).getPei_type().equals("1")){
                                holder.mtvLeft1.setVisibility(View.GONE);
                            }else {
                                holder.mtvLeft1.setVisibility(View.VISIBLE);
                            }
                        }else if(dataList.get(position).getPei_type().equals("1")){
                            holder.mtvLeft1.setVisibility(View.GONE);
                        }else  if (dataList.get(position).getPei_type().equals("3")){
                            holder.mtvLeft1.setVisibility(View.VISIBLE);
                        }
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 3://商家配送中

                        holder.tvRight.setText("催单");
                /*confirm*/
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("reminder",position);
                            }
                        });

                        holder.tvLeft.setText("确认送达");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.mtvLeft1.setText("申请退款");
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(listener!=null){
                                    listener.OnClick("refundorder",position);
                                }
                            }
                        });
                        if(!dataList.get(position).getPei_type().equals("3")) {
                            if (Long.parseLong(dataList.get(position).cui_time) > 0) {
                                holder.tvRight.setVisibility(View.GONE);
                            } else {
                                holder.tvRight.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.tvRight.setVisibility(View.GONE);
                            holder.mtvLeft1.setText("确认自提");
                        }
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.VISIBLE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;

                    case 4://商家已送达

                        holder.mtvLeft1.setText("再来一单");
                /*confirm*/
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });
                        holder.mtvLeft1.setVisibility(View.VISIBLE);
                        holder.tvLeft.setText("确认送达");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.tvRight.setText("申请退款");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(listener!=null){
                                    listener.OnClick("refundorder",position);
                                }
                            }
                        });
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.VISIBLE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 8://等待评价
                        holder.tvLeft.setText("再来一单");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        if(dataList.get(position).comment_status.equals("1")){
                            holder.tvRight.setText("查看评价");
                            holder.tvRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (listener != null)
                                        listener.OnClick("lookevaluate",position);
                                }
                            });

                        }else {
                            holder.tvRight.setText("去评价");
                            holder.tvRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (listener != null)
                                        listener.OnClick("toevaluate",position);
                                }
                            });
                        }
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                }




            } else {//订单异常
                if (dataList.get(position).refund.equals("0")) {//TODO 退款处理中
                    holder.tvLeft.setText("再来一单");
                    holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null)
                                listener.OnClick("again",position);
                        }
                    });
                    holder.tvRight.setVisibility(View.GONE);
                    holder.tvLeft.setVisibility(View.VISIBLE);
                    holder.mtvLeft1.setVisibility(View.GONE);
                    holder.mTvleft2.setVisibility(View.GONE);
                    holder.laytime.setVisibility(View.GONE);


                } else if (dataList.get(position).refund.equals("1")) {//TODO 同意退款
                    holder.tvRight.setText("再来一单");
                    holder.tvRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null)
                                listener.OnClick("again",position);
                        }
                    });
                    holder.tvRight.setVisibility(View.VISIBLE);
                    holder.tvLeft.setVisibility(View.GONE);
                    holder.mtvLeft1.setVisibility(View.GONE);
                    holder.mTvleft2.setVisibility(View.GONE);
                    holder.laytime.setVisibility(View.GONE);
                }
                if (dataList.get(position).refund.equals("2")) {//商家拒绝退款
                    if(dataList.get(position).getPei_type().equals("1")){
                        holder.tvRight.setText("申请客服介入");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("kefu",position);
                            }
                        });
                        holder.tvLeft.setText("确认送达");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                    }else{
                        holder.tvRight.setText("确认到达");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.tvLeft.setText("申请客服介入");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("kefu",position);
                            }
                        });
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);

                    }


                }

                if(dataList.get(position).refund.equals("3")){//客服介入中
                    if(dataList.get(position).getOrder_status().equals("8")){//平台拒绝退款
                        holder.tvRight.setText("再来一单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again", position);
                            }
                        });
                        if(dataList.get(position).getComment_status().equals("1")){
                            holder.tvLeft.setText("查看评价");
                            holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (listener != null)
                                        listener.OnClick("lookevaluate", position);
                                }
                            });

                        }else {
                            holder.tvLeft.setText("去评价");
                            holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (listener != null)
                                        listener.OnClick("toevaluate", position);
                                }
                            });

                        }

                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);

                    }else {

                        holder.tvRight.setText("再来一单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again", position);
                            }
                        });
                        holder.tvLeft.setText("确认到达");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm", position);
                            }
                        });
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.GONE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                    }

                }

            }


        } else  if(dataList.get(position).pay_status.equals("0")){//未支付
            if(dataList.get(position).getOnline_pay().equals("0")){//货到付款

                switch (Integer.parseInt(dataList.get(position).order_status)) {
                    case -1://已取消
                        holder.tvRight.setText("再来一单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.GONE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 0://等待商户接单
                        holder.tvRight.setText("再来一单");
                        holder.tvRight.setTextColor(context.getResources().getColor(R.color.title_color));
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });

                        holder.tvLeft.setText("取消订单");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("CancleOrder",position);
                            }
                        });
                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 1://商户已结单 预订单
                        holder.tvRight.setText("催单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("reminder",position);
                            }
                        });
                        holder.mtvLeft1.setText("确认送达");
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        if (!dataList.get(position).getPei_type().equals("3")) {
                            if (Long.parseLong(dataList.get(position).cui_time) > 0) {
                                holder.tvRight.setVisibility(View.GONE);
                            } else {
                                holder.tvRight.setVisibility(View.VISIBLE);
                            }
                            if(dataList.get(position).getPei_type().equals("1")){
                                holder.mtvLeft1.setVisibility(View.GONE);
                            }else{
                                holder.mtvLeft1.setVisibility(View.VISIBLE);
                            }

                        }else {
                            holder.tvRight.setVisibility(View.GONE);
                            holder.mtvLeft1.setText("确认自提");
                            holder.mtvLeft1.setVisibility(View.VISIBLE);
                        }


                        holder.tvLeft.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 2://商家已接单
                        holder.tvRight.setText("催单");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("reminder",position);
                            }
                        });
                        holder.mtvLeft1.setText("确认送达");
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        if(!dataList.get(position).getPei_type().equals("3")) {
                            if (Long.parseLong(dataList.get(position).cui_time) > 0) {
                                holder.tvRight.setVisibility(View.GONE);
                            } else {
                                holder.tvRight.setVisibility(View.VISIBLE);
                            }
                            if(dataList.get(position).getPei_type().equals("1")){
                                holder.mtvLeft1.setVisibility(View.GONE);
                            }
                            holder.mtvLeft1.setVisibility(View.VISIBLE);
                        }else {
                            holder.tvRight.setVisibility(View.GONE);
                            holder.mtvLeft1.setText("确认自提");
                            holder.mtvLeft1.setVisibility(View.VISIBLE);
                        }
                        holder.tvLeft.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 3://商家配送中

                        holder.tvRight.setText("催单");
                /*confirm*/
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("reminder",position);
                            }
                        });

                        holder.tvLeft.setText("确认送达");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.mtvLeft1.setText("申请退款");
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(listener!=null){
                                    listener.OnClick("refundorder",position);
                                }
                            }
                        });
                        if(!dataList.get(position).getPei_type().equals("3")) {
                            if (Long.parseLong(dataList.get(position).cui_time) > 0) {
                                holder.tvRight.setVisibility(View.GONE);
                            } else {
                                holder.tvRight.setVisibility(View.VISIBLE);
                            }
                        }else {
                            holder.tvRight.setVisibility(View.GONE);
                            holder.mtvLeft1.setText("确认自提");
                        }
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;

                    case 4://商家已送达

                        holder.mtvLeft1.setText("再来一单");
                /*confirm*/
                        holder.mtvLeft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });
                        holder.mtvLeft1.setVisibility(View.VISIBLE);
                        holder.tvLeft.setText("确认送达");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("confirm",position);
                            }
                        });
                        holder.tvRight.setText("申请退款");
                        holder.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(listener!=null){
                                    listener.OnClick("refundorder",position);
                                }
                            }
                        });
                        holder.tvRight.setVisibility(View.GONE);
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.VISIBLE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                    case 8://等待评价
                        holder.tvLeft.setText("再来一单");
                        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null)
                                    listener.OnClick("again",position);
                            }
                        });
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        if(dataList.get(position).comment_status.equals("1")){
                            holder.tvRight.setText("查看评价");
                            holder.tvRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (listener != null)
                                        listener.OnClick("lookevaluate",position);
                                }
                            });

                        }else {
                            holder.tvRight.setText("去评价");
                            holder.tvRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (listener != null)
                                        listener.OnClick("toevaluate",position);
                                }
                            });


                        }

                        holder.tvRight.setVisibility(View.VISIBLE);
                        holder.tvLeft.setVisibility(View.VISIBLE);
                        holder.mtvLeft1.setVisibility(View.GONE);
                        holder.mTvleft2.setVisibility(View.GONE);
                        holder.laytime.setVisibility(View.GONE);
                        break;
                }

            }else {
                if (dataList.get(position).order_status.equals("-1")) {//取消订单

                    holder.tvRight.setText("再来一单");
                    holder.tvRight.setTextColor(context.getResources().getColor(R.color.title_color));
                    holder.tvRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null)
                                listener.OnClick("again", position);
                        }
                    });
                    holder.tvRight.setVisibility(View.VISIBLE);
                    holder.tvLeft.setVisibility(View.GONE);
                    holder.mtvLeft1.setVisibility(View.GONE);
                    holder.mTvleft2.setVisibility(View.GONE);
                    holder.laytime.setVisibility(View.GONE);
                } else if (dataList.get(position).order_status.equals("0")) {//未付款
                    holder.tvOrderStatus.setTextColor(context.getResources().getColor(R.color.color_yan));
                    holder.tvRight.setTextColor(context.getResources().getColor(R.color.color_yan));
                    holder.tvLeft.setText("取消订单");
                    holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnClick("CancleOrder", position);
                            }
                        }
                    });
                    holder.tvRight.setText("去支付");
                    holder.tvRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnClick("topay", position);
                            }
                        }
                    });
                    if (Integer.parseInt(dataList.get(position).getPay_ltime()) > 0) {
                        holder.laytime.setVisibility(View.VISIBLE);
                        Date dt = new Date();
                        long str = Integer.parseInt(dataList.get(position).pay_ltime) * 60 * 1000 - (dt.getTime() - Long.parseLong(dataList.get(position).dateline) * 1000);
                        holder.time.start(str);
                        holder.time.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                            @Override
                            public void onEnd(CountdownView cv) {
                                holder.laytime.setVisibility(View.GONE);
                                if (listener != null) {
                                    listener.OnClick("CancleOrder2", position);
                                }
                            }
                        });
                    }
                    holder.laytime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.OnClick("topay", position);
                            }
                        }
                    });

                    holder.tvRight.setVisibility(View.GONE);
                    holder.tvLeft.setVisibility(View.VISIBLE);
                    holder.mtvLeft1.setVisibility(View.GONE);
                    holder.mTvleft2.setVisibility(View.GONE);

                }
            }

        }

        holder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                  listener.OnClick("all",position);
                }
            }
        });

        holder.shoplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.OnClick("goShop",position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }


    public interface OnClickListener {
        void OnClick(String goPay,int postion);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_shop_head)
        RoundImageView ivShopHead;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.tv_order_time)
        TextView tvOrderTime;
        @Bind(R.id.tv_order_status)
        TextView tvOrderStatus;
        @Bind(R.id.order_commodity)
        TextView orderCommodity;
        @Bind(R.id.tv_order_price)
        TextView tvOrderPrice;
        @Bind(R.id.tv_left)
        TextView tvLeft;
        @Bind(R.id.tv_right)
        TextView tvRight;
        @Bind(R.id.bottom_lay)
        LinearLayout Laybottom;
        @Bind(R.id.left1_tv)
        TextView mtvLeft1;
        @Bind(R.id.left2_tv)
        TextView mTvleft2;
        @Bind(R.id.all_rlay)
        LinearLayout all;
        @Bind(R.id.laytime)
        LinearLayout laytime;
        @Bind(R.id.countdown)
        CountdownView time;
        @Bind(R.id.layshop)
        LinearLayout shoplayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
