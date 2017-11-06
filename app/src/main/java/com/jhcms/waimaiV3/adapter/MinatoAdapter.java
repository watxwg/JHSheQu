package com.jhcms.waimaiV3.adapter;

import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;

import static com.jhcms.waimaiV3.R.id.count;

/**
 * Created by Wyj on 2017/5/8
 * TODO:凑一凑adapter
 */
public class MinatoAdapter extends RecyclerView.Adapter<MinatoAdapter.ViewHolder> {
    private WaiMaiInStoreSearchActivity searchActivity;
    private SparseArray<Goods> selectedList;
    private WaiMaiShopDetailActivity shopDetailActivity;
    private WaiMaiShopActivity activity;
    private ArrayList<Goods> dataList;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public MinatoAdapter(WaiMaiShopActivity activity, ArrayList<Goods> dataList) {
        this.activity = activity;
        this.dataList = dataList;
        selectedList = WaiMaiShopActivity.getSelectedList();
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    public MinatoAdapter(WaiMaiShopActivity activity, WaiMaiShopDetailActivity shopDetailActivity, ArrayList<Goods> dataList) {
        this.activity = activity;
        this.shopDetailActivity = shopDetailActivity;
        this.dataList = dataList;
        selectedList = WaiMaiShopActivity.getSelectedList();
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    public MinatoAdapter(WaiMaiShopActivity activity, WaiMaiInStoreSearchActivity searchActivity, ArrayList<Goods> dataList) {
//        this.activity = activity;
        this.searchActivity = searchActivity;
        this.dataList = dataList;
        selectedList = WaiMaiShopActivity.getSelectedList();
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
        Goods goods = selectedList.get(Integer.parseInt(dataList.get(position).product_id));
        if (goods == null) {
            goods = dataList.get(position);
        }
        holder.bindData(goods);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Goods item;
        private TextView tvCost, tvCount, tvAdd, tvMinus, tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCost = (TextView) itemView.findViewById(R.id.tvCost);
            tvCount = (TextView) itemView.findViewById(count);
            tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
            tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
        }

        private int goods_number;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvAdd:
                    goods_number++;
                    if (goods_number > item.sale_sku) {
                        ToastUtil.show("库存不足");
                        goods_number--;
                        return;
                    }
                    if (goods_number < 2) {
                        tvMinus.setEnabled(true);
                    }
                    if (null != shopDetailActivity) {
                        shopDetailActivity.add(item);
                    } else if (null != searchActivity) {
                        searchActivity.add(item, true);
                    } else {
                        activity.add(item, true);
                    }
                    break;
                case R.id.tvMinus:
                    if (goods_number == 0) {
                        tvMinus.setEnabled(false);
                    }
                    if (null != shopDetailActivity) {
                        shopDetailActivity.remove(item);
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
            tvName.setText(item.name);
            if (searchActivity != null) {
                item.count = searchActivity.getSelectedItemCountById(Integer.parseInt(item.productId));
            } else {
                item.count = activity.getSelectedItemCountById(Integer.parseInt(item.productId));
            }

            goods_number = item.count;
            tvCost.setText(nf.format(Double.parseDouble(item.price)));
            tvCount.setText(String.valueOf(item.count));
        }
    }

}
