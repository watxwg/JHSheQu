package com.jhcms.waimaiV3.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiInStoreSearchActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopActivity;
import com.jhcms.waimaiV3.activity.WaiMaiShopDetailActivity;
import com.jhcms.waimaiV3.model.Goods;

import java.text.NumberFormat;

/**
 * Created by Wyj on 2017/5/8
 * TODO: 购物车adapter
 */
public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
    private WaiMaiInStoreSearchActivity searchActivity;
    private WaiMaiShopDetailActivity detailActivity;
    private WaiMaiShopActivity activity;
    private SparseArray<Goods> dataList;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public SelectAdapter(WaiMaiShopActivity activity, SparseArray<Goods> dataList) {
        this.activity = activity;
        this.dataList = dataList;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    public SelectAdapter(WaiMaiShopDetailActivity detailActivity, SparseArray<Goods> dataList) {
        this.dataList = dataList;
        this.detailActivity = detailActivity;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(detailActivity);
    }

    public SelectAdapter(WaiMaiInStoreSearchActivity searchActivity, SparseArray<Goods> dataList) {
        this.dataList = dataList;
        this.searchActivity = searchActivity;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(searchActivity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_selected_goods_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goods item = dataList.valueAt(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Goods item;
        private TextView tvCost, tvCount, tvAdd, tvMinus, tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCost = (TextView) itemView.findViewById(R.id.tvCost);
            tvCount = (TextView) itemView.findViewById(R.id.count);
            tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
            tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvAdd:
                    if (item.sale_sku < item.count + 1) {
                        ToastUtil.show("库存不足");
                        return;
                    }
                    if (null != detailActivity) {
                        detailActivity.add(item);
                    } else if (null != searchActivity) {
                        searchActivity.add(item, true);
                    } else {
                        activity.add(item, true);
                    }
                    break;
                case R.id.tvMinus:
                    if (null != detailActivity) {
                        detailActivity.remove(item);
                    } else if (null != searchActivity) {
                        searchActivity.remove(item, true);
                    } else {
                        activity.remove(item, true);
                    }
                    break;
                default:
                    break;
            }
        }

        public void bindData(Goods item) {
            this.item = item;
            if (!TextUtils.isEmpty(item.spec_id) && !item.spec_id.equals(0)) {
                tvName.setText(item.name);
//                tvName.setText(item.productsEntity.title+"("+);
                tvCost.setText(nf.format(Double.parseDouble(item.price)));
                tvCount.setText(String.valueOf(item.count));
            } else {
                tvName.setText(item.title);
                tvCost.setText(nf.format(Double.parseDouble(item.productsEntity.price)));
                tvCount.setText(String.valueOf(item.count));
            }

        }
    }
}
