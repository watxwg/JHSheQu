package com.jhcms.mall.dialog.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.mall.adapter.CommonAdapter;
import com.jhcms.mall.adapter.viewholder.CommonViewHolder;
import com.jhcms.mall.model.SpecificationInfoModel;
import com.jhcms.waimaiV3.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 作者：WangWei
 * 日期：2017/9/16 11:00
 * 描述：规格对话框
 */

public class GuigeAdapter extends CommonAdapter<SpecificationInfoModel> {
    /**
     * 用于记录选择的规格
     */
    private final SparseArray<SpecificationInfoModel.SpecificationValues> selectValues;
    private OnITagClickListener mTagListener;

    public GuigeAdapter(Context context, List<SpecificationInfoModel> data) {
        super(context, data, R.layout.mall_list_item_guige);
        selectValues = new SparseArray<>();
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        SpecificationInfoModel specificationInfoModel = mData.get(position);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void convertViewData(CommonViewHolder holder, SpecificationInfoModel bean) {
        holder.setTextViewText(R.id.tv_name, bean.getTitle());
        int adapterPosition = holder.getAdapterPosition();
        TagFlowLayout tagFlowLayout = holder.<TagFlowLayout>getView(R.id.tfl_tag);
        tagFlowLayout.setMaxSelectCount(1);
        TagAdapter<SpecificationInfoModel.SpecificationValues> tagAdapter = new TagAdapter<SpecificationInfoModel.SpecificationValues>(bean.getValues()) {
            @Override
            public View getView(FlowLayout parent, int position, SpecificationInfoModel.SpecificationValues specificationValues) {
                TextView inflate = (TextView) GuigeAdapter.this.inflater.inflate(R.layout.mall_list_item_tag, parent, false);
                inflate.setText(specificationValues.getTitle());
                //将数据保存到view中，用于在点击时取出选中数据
                inflate.setTag(specificationValues);
                return inflate;
            }
        };
        tagFlowLayout.setOnTagClickListener((v, position, parent) -> {
            Set<Integer> selectedList = tagFlowLayout.getSelectedList();

            SpecificationInfoModel.SpecificationValues values = (SpecificationInfoModel.SpecificationValues) v.getTag();
            if (values == null) {
                Toast.makeText(mContext, mContext.getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                if (selectedList.size() == 0) {//取消
                    selectValues.remove(adapterPosition);
                } else {//选中
                    selectValues.append(adapterPosition, values);
                }
            }
            if (mTagListener != null) {
                mTagListener.onTagClick(selectValues);
            }
            return true;
        });

        tagFlowLayout.setAdapter(tagAdapter);
    }

    /**
     * 获取选中的数据
     *
     * @return
     */
    public SparseArray<SpecificationInfoModel.SpecificationValues> getSelectValues() {

        return selectValues;

    }

    /**
     * tag点击接口
     */
    public interface OnITagClickListener {
        void onTagClick(SparseArray<SpecificationInfoModel.SpecificationValues> selectTagValues);
    }

    public void setOnITagClickListener(OnITagClickListener listener) {
        mTagListener = listener;
    }
}
