package com.jhcms.waimaiV3.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.model.MyAddress;
import com.jhcms.common.widget.RecycleViewBaseAdapter;
import com.jhcms.common.widget.SuperViewHolder;
import com.jhcms.waimaiV3.R;

/**
 * Created by Wyj on 2017/4/17.
 */
public class AddressAdapter extends RecycleViewBaseAdapter<MyAddress> {
    TextView tvInfo;
    TextView tvAdress;
    TextView tvType;
    TextView tvNotIn;
    ImageView ivDelete;
    ImageView ivModify;
    ImageView ivSecect;
    RelativeLayout root;
    private OnClickListener listener;
    private String addr_id;

    public AddressAdapter(Context context) {
        super(context);
    }

    public void setType(String addr_id) {
        this.addr_id = addr_id;
    }

    public interface OnClickListener {
        void adressClick(String type, int position);
    }


    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getLayoutId() {
        return R.layout.adapter_address_item;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        tvInfo = holder.getView(R.id.tv_info);
        tvAdress = holder.getView(R.id.tv_adress);
        tvType = holder.getView(R.id.tv_type);
        ivDelete = holder.getView(R.id.iv_delete);
        ivModify = holder.getView(R.id.iv_modify);
        ivSecect = holder.getView(R.id.iv_secect);
        tvNotIn = holder.getView(R.id.tv_not_in);
        root = holder.getView(R.id.ll_root);
        MyAddress myAddress = mDataList.get(position);
        tvInfo.setText(myAddress.contact + "-" + myAddress.mobile);
        tvAdress.setText(myAddress.addr + myAddress.house);
        switch (myAddress.type) {
            case 1:
                tvType.setText("家");
                tvType.setBackgroundResource(R.drawable.shap_bg_yan_radius);
                break;
            case 2:
                tvType.setText("公司");
                tvType.setBackgroundResource(R.drawable.shap_bg_blue_radius);
                break;
            case 3:
                tvType.setText("学校");
                tvType.setBackgroundResource(R.drawable.shap_bg_theme_radius);
                break;
            case 4:
                tvType.setText("其他");
                tvType.setBackgroundResource(R.drawable.shap_bg_gray_radius);
                break;
        }
        /**
         *
         *addr_id与列表中的相同
         * 相同隐藏编辑删除按钮
         * 不同显示编辑删除按钮
         */
        if (null != addr_id) {
            if (mDataList.get(position).addr_id.equals(addr_id)) {
                ivSecect.setVisibility(View.VISIBLE);
                ivSecect.setSelected(true);
            } else {
                ivSecect.setVisibility(View.GONE);
            }
            ivDelete.setVisibility(View.GONE);
            ivModify.setVisibility(View.GONE);
        } else {
            tvNotIn.setVisibility(View.GONE);
            ivSecect.setVisibility(View.GONE);
            ivDelete.setVisibility(View.VISIBLE);
            ivModify.setVisibility(View.VISIBLE);
        }


        /**
         * is_in==0时地址不可选背景变为灰色
         * */
        if (null != mDataList.get(position).is_in && mDataList.get(position).is_in.equals("0")) {
            root.setBackgroundResource(R.color.lineGray);
            tvNotIn.setVisibility(View.VISIBLE);
            root.setEnabled(false);
            tvInfo.setTextColor(mContext.getResources().getColor(R.color.third_txt_color));
            tvAdress.setTextColor(mContext.getResources().getColor(R.color.third_txt_color));



        } else {
            root.setBackgroundResource(R.drawable.shap_bg_ripples_line);
            tvInfo.setTextColor(mContext.getResources().getColor(R.color.txt_color));
            tvAdress.setTextColor(mContext.getResources().getColor(R.color.second_txt_color));
            tvNotIn.setVisibility(View.GONE);
            root.setEnabled(true);
        }

        ivModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.adressClick("modify", position);
                }
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.adressClick("delete", position);
                }
            }
        });
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.adressClick("confirm", position);
                }
            }
        });
    }
}
