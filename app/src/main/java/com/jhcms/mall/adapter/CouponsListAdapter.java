package com.jhcms.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.mall.model.CouponsInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/9/13 14:44
 * 描述：店铺详情优惠券
 */

public class CouponsListAdapter extends CommonAdapter<CouponsInfoModel> {

    public CouponsListAdapter(Context context, List<CouponsInfoModel> data, @LayoutRes int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convertViewData(CommonViewHolder holder, CouponsInfoModel bean) {
        try {

            holder.<TextView>getView(R.id.tv_price).setText((int) Float.parseFloat(bean.getCoupon_amount()) + "");
            holder.<TextView>getView(R.id.tv_order_ammount).setText(mContext.getString(R.string.mall_满使用, (int) Float.parseFloat(bean.getOrder_amount())));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, mContext.getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();

        }
    }
}
