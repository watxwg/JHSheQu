package com.jhcms.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.SlidingButtonView;
import com.jhcms.mall.model.GoodsInfo;
import com.jhcms.waimaiV3.R;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/25.
 * TODO
 */

public class MallShopCartCommAdapter extends RecyclerView.Adapter<MallShopCartCommAdapter.MyViewHolder> implements SlidingButtonView.IonSlidingButtonListener {
    private NumberFormat nf;
    private Context mContext;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private SlidingButtonView mMenu = null;
    private List<GoodsInfo> data;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private int groupPosition;

    public void setData(List<GoodsInfo> data, int groupPosition) {
        this.data = data;
        this.groupPosition = groupPosition;
        notifyDataSetChanged();
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    public void setOnCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int childPosition, View showCountView, boolean isChecked);
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    /**
     * 侧滑删除的接口
     */
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);

        void onDeleteBtnCilck(View view, int position);
    }

    public void setIonSlidingViewClickListener(IonSlidingViewClickListener mIDeleteBtnClickListener) {
        this.mIDeleteBtnClickListener = mIDeleteBtnClickListener;
    }

    public MallShopCartCommAdapter(Context context) {
        mContext = context;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_car_comm_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int childPosition) {
        holder.tvCommName.setText(data.get(childPosition).getName());
        holder.tvCommNum.setText(data.get(childPosition).getCount() + "");
        if (TextUtils.isEmpty(data.get(childPosition).getDesc())) {
            holder.tvCommSpec.setVisibility(View.GONE);
        } else {
            holder.tvCommSpec.setVisibility(View.VISIBLE);
            holder.tvCommSpec.setText("规格:" + data.get(childPosition).getDesc());
        }
        holder.tvCommPices.setText(nf.format(data.get(childPosition).getPrice()));
        Utils.LoadStrPicture(mContext, Api.IMAGE_URL + data.get(childPosition).getGoodsImg(), holder.ivCommPic);

        holder.layoutContent.getLayoutParams().width = Utils.getScreenW(mContext);
        holder.layoutContent.setOnClickListener(v -> {
            //判断是否有删除菜单打开
            if (menuIsOpen()) {
                closeMenu();//关闭菜单
            } else {
                int n = holder.getLayoutPosition();
                if (mIDeleteBtnClickListener != null) {
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }
            }
        });
        //删除监听
        holder.tvDelete.setOnClickListener(v -> {
            int n = holder.getLayoutPosition();
            if (mIDeleteBtnClickListener != null) {
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });

        //checkBox的监听
        holder.ivComm.setOnClickListener(v -> {
            data.get(childPosition).setChoosed(((CheckBox) v).isChecked());
            if (null != checkInterface) {
                checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
            }
        });
        holder.ivComm.setChecked(data.get(childPosition).isChoosed());
        holder.ivCommAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modifyCountInterface != null) {
                    modifyCountInterface.doIncrease(childPosition, holder.tvCommNum, holder.ivComm.isChecked());// 暴露增加接口
                }
            }
        });
        holder.ivCommLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modifyCountInterface != null) {
                    modifyCountInterface.doDecrease( childPosition, holder.tvCommNum, holder.ivComm.isChecked());// 暴露删减接口
                }
            }
        });


    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.iv_comm)
        CheckBox ivComm;
        @Bind(R.id.iv_comm_pic)
        ImageView ivCommPic;
        @Bind(R.id.tv_comm_name)
        TextView tvCommName;
        @Bind(R.id.tv_comm_spec)
        TextView tvCommSpec;
        @Bind(R.id.tv_comm_pices)
        TextView tvCommPices;
        @Bind(R.id.tv_comm_sold)
        TextView tvCommSold;
        @Bind(R.id.iv_comm_less)
        ImageView ivCommLess;
        @Bind(R.id.tv_comm_num)
        TextView tvCommNum;
        @Bind(R.id.iv_comm_add)
        ImageView ivCommAdd;
        @Bind(R.id.layout_content)
        RelativeLayout layoutContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ((SlidingButtonView) itemView).setSlidingButtonListener(MallShopCartCommAdapter.this);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

}
