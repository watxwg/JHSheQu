package com.jhcms.mall.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.mall.activity.MallProductDetailActivity;
import com.jhcms.mall.activity.MallShopDetailsActivity;
import com.jhcms.mall.model.GoodsInfo;
import com.jhcms.mall.model.StoreInfo;
import com.jhcms.waimaiV3.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin
 * on 2017/8/25.
 * TODO
 */

public class MallShopCartAdapter extends RecyclerView.Adapter<MallShopCartAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    /**
     * 是否全选
     */
    private boolean isSelectAll = false;
    private List<StoreInfo> groups;
    private Map<String, List<GoodsInfo>> children;
    private MallShopCartCommAdapter childrenAdapter;
    private OnItemClickListener itemClickListener;

    public MallShopCartAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.adapter_mall_shop_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return groups == null ? 0 : groups.size();
    }

    public void setData(List<StoreInfo> groups, Map<String, List<GoodsInfo>> children) {
        this.groups = groups;
        this.children = children;

    }

    public Object getChild(int groupPosition, int childPosition) {
        List<GoodsInfo> childs = children.get(groups.get(groupPosition).getShopId());
        return childs.get(childPosition);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cb_allComm)
        CheckBox cbAllComm;
        @Bind(R.id.tv_shop_name)
        TextView tvShopName;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;
        @Bind(R.id.rv_shop_cart_item)
        RecyclerView rvShopCartItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int groupPosition) {
            StoreInfo group = groups.get(groupPosition);
            tvShopName.setText(group.getName());
            cbAllComm.setOnClickListener(v -> {
                group.setChoosed(((CheckBox) v).isChecked());
                if (null != itemClickListener) {
                    itemClickListener.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
                }

            });
            cbAllComm.setChecked(group.isChoosed());
            childrenAdapter = new MallShopCartCommAdapter(context);
            rvShopCartItem.setNestedScrollingEnabled(true);
            rvShopCartItem.setFocusable(false);
            rvShopCartItem.setAdapter(childrenAdapter);
            childrenAdapter.setData(children.get(group.getShopId()), groupPosition);
            rvShopCartItem.setLayoutManager(new LinearLayoutManager(context));
            childrenAdapter.setOnCheckInterface(new MallShopCartCommAdapter.CheckInterface() {
                @Override
                public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
                    boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
                    List<GoodsInfo> childs = children.get(group.getShopId());
                    for (int i = 0; i < childs.size(); i++) {
                        // 不全选中
                        if (childs.get(i).isChoosed() != isChecked) {
                            allChildSameState = false;
                            break;
                        }
                    }
                    //获取店铺选中商品的总金额
                    if (allChildSameState) {
                        group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
                    } else {
                        group.setChoosed(false);// 否则，组元素一律设置为未选中状态
                    }
                    if (null != itemClickListener) {
                        itemClickListener.isCheckAll();
                    }

                    notifyDataSetChanged();
                }
            });
            childrenAdapter.setIonSlidingViewClickListener(new MallShopCartCommAdapter.IonSlidingViewClickListener() {
                @Override
                public void onItemClick(View view, int childPosition) {
                    context.startActivity(MallProductDetailActivity.generateIntent(context, Integer.parseInt(children.get(group.getShopId()).get(childPosition).getGoodId())));
                }

                @Override
                public void onDeleteBtnCilck(View view, int childPosition) {
                    AlertDialog alert = new AlertDialog.Builder(context).create();
                    alert.setTitle("操作提示");
                    alert.setMessage("您确定要将该商品从购物车中移除吗？");
                    alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (itemClickListener != null) {
                                        itemClickListener.childDelete(groupPosition, childPosition);
                                    }
                                }
                            });
                    alert.show();
                }
            });
            childrenAdapter.setModifyCountInterface(new MallShopCartCommAdapter.ModifyCountInterface() {
                @Override
                public void doIncrease(int childPosition, View showCountView, boolean isChecked) {
                    if (null != itemClickListener) {
                        itemClickListener.doIncrease(groupPosition, childPosition, showCountView, isChecked);
                    }
                }

                @Override
                public void doDecrease(int childPosition, View showCountView, boolean isChecked) {
                    if (null != itemClickListener) {
                        itemClickListener.doDecrease(groupPosition, childPosition, showCountView, isChecked);
                    }
                }
            });
            ivDelete.setOnClickListener(v -> {
                if (null != itemClickListener) {
                    itemClickListener.detele(groupPosition);
                }
            });
            tvShopName.setOnClickListener(v -> {
                context.startActivity(MallShopDetailsActivity.generateIntent(context, Integer.parseInt(group.getShopId())));
            });


        }
    }


    public interface OnItemClickListener {
        /**
         * 店铺选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 店铺商品是否全选
         */
        void isCheckAll();

        /**
         * 清空店铺购物车
         */
        void detele(int groupPosition);

        /**
         * 删除商品
         */
        void childDelete(int groupPosition, int childPosition);

        /**
         * 增加商品操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减商品操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
