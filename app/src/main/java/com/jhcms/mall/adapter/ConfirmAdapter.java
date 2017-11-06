package com.jhcms.mall.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.model.CouponOfOrderModel;
import com.jhcms.mall.model.ShopOrderInfoModel;
import com.jhcms.waimaiV3.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangyujie
 * on 2017/9/18.13:59
 * TODO
 */

public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.MyViewHolder> {
    private static final String TAG = ConfirmAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater inflater;
    private ImageView ivCommPic;
    private TextView tvCommName;
    private TextView tvCommSpec;
    private TextView tvCommNum;
    private TextView tvCommPices;
    private NumberFormat nf;
    private View line;
    private OnClickCouponListener listener;
    private List<ShopOrderInfoModel> mData;
    private OnCouponChangedListener mOnCouponChangedListener;

    public ConfirmAdapter(Context context, List<ShopOrderInfoModel> data) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_confirm_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShopOrderInfoModel shopOrderInfoModel = mData.get(position);
        if (shopOrderInfoModel.getFreight() > 0) {
            holder.tvDeliveryFee.setText(nf.format(shopOrderInfoModel.getFreight()));
        } else {
            holder.tvDeliveryFee.setText(R.string.免运费);
        }

        holder.tvNumberProducts.setText(mContext.getString(R.string.mall_共件商品, shopOrderInfoModel.getTotal_number()));
        holder.tvTotal.setText(mContext.getString(R.string.mall_¥f, shopOrderInfoModel.getTotal_price()));
        holder.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shopOrderInfoModel.setMarkInfo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (!TextUtils.isEmpty(shopOrderInfoModel.getCoupon_id())) {//使用了优惠券
            //显示优惠券信息
            if (shopOrderInfoModel.getCoupons() != null) {
                List<CouponOfOrderModel> coupons = shopOrderInfoModel.getCoupons();
                for (int i = 0; i < coupons.size(); i++) {
                    CouponOfOrderModel couponOfOrderModel = coupons.get(i);
                    if (shopOrderInfoModel.getCoupon_id().equals(couponOfOrderModel.getCoupon_id())) {
                        holder.tvCoupon.setText(couponOfOrderModel.getTitle()+"-"+mContext.getString(R.string.mall_¥f,couponOfOrderModel.getCoupon_amount()));
                        break;
                    }
                }
            }
        } else {//未使用优惠券
            if (shopOrderInfoModel.getCoupons() != null && shopOrderInfoModel.getCoupons().size() > 0) {
                holder.tvCoupon.setText(mContext.getString(R.string.mall_使用优惠券));
                holder.tvCoupon.setTextColor(Color.parseColor(mContext.getString(R.string.mall_color_14AAE4)));
                holder.llCoupon.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.clickCoupon(position, shopOrderInfoModel.getCoupons(), coupon -> {
                            if (!coupon.equals(shopOrderInfoModel.getCoupon_id())) {
                                shopOrderInfoModel.setCoupon_id(coupon.getCoupon_id());
                                if (mOnCouponChangedListener != null) {
                                    mOnCouponChangedListener.onCouponChanged();
                                }
                            }

                        });
                    }
                });
            } else {
                holder.tvCoupon.setText(mContext.getString(R.string.mall_暂无优惠券));
                holder.tvCoupon.setTextColor(Color.parseColor(mContext.getString(R.string.mall_color_999999)));
            }
        }

        HashMap<String, ShopOrderInfoModel.ProductOrderInfo> cart_goods = shopOrderInfoModel.getCart_goods();
        if (cart_goods != null && cart_goods.size() > 0) {
            ArrayList<ShopOrderInfoModel.ProductOrderInfo> productOrderInfos = new ArrayList<>(cart_goods.values());
            String not_pei = productOrderInfos.get(0).getNot_pei();
            if ("1".equals(not_pei)) {
                holder.ivNotInScope.setVisibility(View.VISIBLE);
            } else {
                holder.ivNotInScope.setVisibility(View.GONE);
            }
            initAllComm(holder, productOrderInfos);
        }
    }

    public void setOnClickCouponListener(OnClickCouponListener listener) {
        this.listener = listener;
    }


    public interface OnClickCouponListener {
        /**
         * @param position
         * @param coupons              优惠券列表
         * @param couponSelectListener 选择优惠券后的回调接口
         */
        void clickCoupon(int position, @NonNull List<CouponOfOrderModel> coupons, @NonNull OnCouponSelectListener couponSelectListener);
    }

    public void setOnCouponChangedListener(OnCouponChangedListener onCouponChangedListener) {
        mOnCouponChangedListener = onCouponChangedListener;
    }

    /**
     * 重选优惠券后的回调接口
     */
    public interface OnCouponChangedListener {
        void onCouponChanged();
    }

    public interface OnCouponSelectListener {
        /**
         * @param coupon 优惠券信息
         */
        void onCouponSelect(CouponOfOrderModel coupon);
    }

    private void initAllComm(MyViewHolder holder, List<ShopOrderInfoModel.ProductOrderInfo> producs) {
        holder.llAllcomm.removeAllViews();

        for (int i = 0; i < producs.size(); i++) {
            ShopOrderInfoModel.ProductOrderInfo productOrderInfo = producs.get(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.mall_confirm_comm, holder.llAllcomm, false);
            line = view.findViewById(R.id.line_staff);
            ivCommPic = (ImageView) view.findViewById(R.id.iv_comm_pic);
            tvCommName = (TextView) view.findViewById(R.id.tv_comment_name);
            tvCommSpec = (TextView) view.findViewById(R.id.tv_comm_spec);
            tvCommNum = (TextView) view.findViewById(R.id.tv_comm_num);
            tvCommPices = (TextView) view.findViewById(R.id.tv_comm_pices);


            tvCommPices.setText(mContext.getString(R.string.mall_¥f, productOrderInfo.getWei_price()));
            tvCommNum.setText("X" + productOrderInfo.getNumber());
            Utils.LoadStrPicture(mContext, Api.IMAGE_URL + productOrderInfo.getPhoto(), ivCommPic);
            tvCommName.setText(productOrderInfo.getTitle());
            if (!TextUtils.isEmpty(productOrderInfo.getStock_real_name())) {
                tvCommSpec.setVisibility(View.VISIBLE);
                tvCommSpec.setText(mContext.getString(R.string.mall_规格f, productOrderInfo.getStock_real_name()));
            } else {
                tvCommSpec.setVisibility(View.GONE);
            }
            if (i == producs.size() - 1) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }
            holder.llAllcomm.addView(view);
        }
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.ll_allcomm)
        LinearLayout llAllcomm;
        @Bind(R.id.tv_delivery_fee)
        TextView tvDeliveryFee;
        @Bind(R.id.ll_delivery_fee)
        LinearLayout llDeliveryFee;
        @Bind(R.id.et_message)
        EditText etMessage;
        @Bind(R.id.tv_coupon)
        TextView tvCoupon;
        @Bind(R.id.ll_coupon)
        LinearLayout llCoupon;
        @Bind(R.id.tv_number_products)
        TextView tvNumberProducts;
        @Bind(R.id.tv_total)
        TextView tvTotal;
        @Bind(R.id.iv_not_in_scope)
        ImageView ivNotInScope;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
