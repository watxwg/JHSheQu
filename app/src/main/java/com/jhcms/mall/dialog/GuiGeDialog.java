package com.jhcms.mall.dialog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.dialog.adapter.GuigeAdapter;
import com.jhcms.mall.model.SpecificaitonDetailModel;
import com.jhcms.mall.model.SpecificationInfoModel;
import com.jhcms.waimaiV3.R;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：WangWei
 * 日期：2017/8/26 16:06
 * 描述：规格选择对话框
 */

public class GuiGeDialog extends BaseDialog {
    private static final String TAG = GuiGeDialog.class.getSimpleName();
    private List<SpecificationInfoModel> mData;
    private GuigeAdapter adapter;
    private HashMap<String, SpecificaitonDetailModel> mHashDetailData;
    //是否选择好规格
    private boolean canConfirm, canChangeNum,hasGuiGe;
    private OnSelectListener mSelectListener;
    private SpecificaitonDetailModel mSpecificationDetail;
    private int kuCun;
    private String priceTemp, photoTemp,kuCunTemp;

    @Override
    public void onStart() {
        setShowBoottom(true);
        super.onStart();
    }

    @Override
    public int provideLauoutId() {
        return R.layout.mall_dialog_layout_guige;
    }

    @Override
    public void initDialog(ViewHolder viewHolder, BaseDialog dialog) {
        //设置默认显示内容
        if(!(TextUtils.isEmpty(priceTemp)||TextUtils.isEmpty(photoTemp))){
            viewHolder.<TextView>getView(R.id.tv_price).setText(getString(R.string.mall_¥f, priceTemp));
            viewHolder.<TextView>getView(R.id.tv_kucun).setText(getString(R.string.mall_库存, kuCunTemp));
            Utils.LoadStrPicture(getContext(), Api.IMAGE_URL + photoTemp, viewHolder.<ImageView>getView(R.id.iv_product_logo));
            if("0".equals(kuCunTemp)){
                viewHolder.<TextView>getView(R.id.tv_num).setText(kuCunTemp);
            }
        }
        if (mData == null) {
            viewHolder.getView(R.id.rv_list).setVisibility(View.GONE);
            viewHolder.getView(R.id.tv_guige).setVisibility(View.GONE);
            canConfirm = true;
            canChangeNum = true;
            hasGuiGe=false;
        } else {
            hasGuiGe=true;
            canConfirm = false;
            canChangeNum = false;
            RecyclerView recyclerView = viewHolder.<RecyclerView>getView(R.id.rv_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new GuigeAdapter(getContext(), mData);
            recyclerView.setAdapter(adapter);
            adapter.setOnITagClickListener(selectTagValues -> handleSelectTagValues(selectTagValues, viewHolder));
        }
        TextView tvNum = viewHolder.<TextView>getView(R.id.tv_num);
        viewHolder.getView(R.id.iv_minus).setOnClickListener(v -> {
            if (!canChangeNum) {
                Toast.makeText(getContext(), getContext().getString(R.string.mall_请选择需要购买的规格), Toast.LENGTH_SHORT).show();
                return;
            }
            int i = Integer.parseInt(tvNum.getText().toString());
            if (i > 1) {
                i--;
                tvNum.setText(i + "");
            }
        });
        viewHolder.getView(R.id.iv_add).setOnClickListener(v -> {
            if (!canChangeNum) {
                Toast.makeText(getContext(), getContext().getString(R.string.mall_请选择需要购买的规格), Toast.LENGTH_SHORT).show();
                return;
            }
            int i = Integer.parseInt(tvNum.getText().toString());
            i++;
            if (i > kuCun) {
                Toast.makeText(getContext(), getContext().getString(R.string.mall_库存不够), Toast.LENGTH_SHORT).show();
                return;
            }
            tvNum.setText(i + "");
        });
        viewHolder.getView(R.id.iv_close).setOnClickListener(v -> dismiss());
        viewHolder.getView(R.id.tv_confirm).setOnClickListener(v -> {
            if (!canConfirm) {
                Toast.makeText(getContext(), getContext().getString(R.string.mall_请选择需要购买的规格), Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (mSelectListener != null) {
                    mSelectListener.onSelect(hasGuiGe,viewHolder.<TextView>getView(R.id.tv_num).getText().toString(),mSpecificationDetail);
                }
//                Log.e(TAG, "initDialog: " + mSpecificationDetail.getStock_real_name());
                dismiss();
            }

        });

    }

    /**
     * 通过选中的规格，改变显示类容
     *
     * @param selectTagValues
     * @param holder
     */
    private void handleSelectTagValues(SparseArray<SpecificationInfoModel.SpecificationValues> selectTagValues, ViewHolder holder) {
        if (selectTagValues.size() != mData.size()) {//是否选择了所有的规格
            canConfirm = false;
            canChangeNum = false;
            mSpecificationDetail = null;
            //重置基本信息
            if(!(TextUtils.isEmpty(priceTemp)||TextUtils.isEmpty(photoTemp))){
                holder.<TextView>getView(R.id.tv_price).setText(getString(R.string.mall_¥f, priceTemp));
                holder.<TextView>getView(R.id.tv_kucun).setText(getString(R.string.mall_库存, kuCun+""));
                Utils.LoadStrPicture(getContext(), Api.IMAGE_URL + photoTemp, holder.<ImageView>getView(R.id.iv_product_logo));
            }

            return;
        }
        canChangeNum = true;
        canConfirm = true;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectTagValues.size(); i++) {
            SpecificationInfoModel.SpecificationValues values = selectTagValues.get(i);
            if (values == null) {
                Toast.makeText(getContext(), getContext().getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                return;
            }
            if (i == 0) {

                sb.append(values.getAttr_value_id());
            } else {
                sb.append("_" + values.getAttr_value_id());
            }
        }
        String key = sb.toString();
        SpecificaitonDetailModel specificaitonDetailModel = mHashDetailData.get(key);
        if (specificaitonDetailModel == null) {
            Log.e(TAG, "handleSelectTagValues: 程序出错，没有找到规格信息");
            return;
        }
        mSpecificationDetail = specificaitonDetailModel;
        Utils.LoadStrPicture(getContext(), Api.IMAGE_URL + specificaitonDetailModel.getPhoto(), holder.<ImageView>getView(R.id.iv_product_logo));
        holder.<TextView>getView(R.id.tv_price).setText(getString(R.string.mall_¥f, specificaitonDetailModel.getWei_price()));
        holder.<TextView>getView(R.id.tv_kucun).setText(getString(R.string.mall_库存, specificaitonDetailModel.getStock()));
        holder.<TextView>getView(R.id.tv_guige).setText(specificaitonDetailModel.getStock_real_name());
        try {
            kuCun = Integer.parseInt(mSpecificationDetail.getStock());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getContext().getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * 在没有选择规格时展示的信息
     * @param price
     * @param kuCun
     * @param photo
     */
    public void setBasicInfo(@NonNull String price, @NonNull String kuCun, @NonNull String photo) {
        try {

            this.kuCun = Integer.parseInt(kuCun);
            priceTemp=price;
            photoTemp= photo;
            kuCunTemp=kuCun;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setBasicInfo: " + getString(R.string.mall_程序出错));

        }
    }


    /**
     * 设置规格数据
     *
     * @param data
     * @param detail
     */
    public void setData(@NonNull List<SpecificationInfoModel> data, @NonNull HashMap<String, SpecificaitonDetailModel> detail) {
        mData = data;
        mHashDetailData = detail;
    }

    public void setOnSelectListener(OnSelectListener listener) {
        mSelectListener = listener;
    }

    public interface OnSelectListener {
        /**
         *
         * @param hasGuiGe 是否有规格信息
         * @param num 选择的数量
         * @param values 若没有规格信息，为null
         */
        void onSelect(boolean hasGuiGe,@NonNull String num,@Nullable SpecificaitonDetailModel values);
    }
}
