package com.jhcms.mall.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.SlidingButtonView;
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

/**
 * 购物车数据适配器
 */
public class ShopcartAdapter extends BaseExpandableListAdapter implements SlidingButtonView.IonSlidingButtonListener {

    private List<StoreInfo> groups;
    private Map<String, List<GoodsInfo>> children;
    private Context context;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    public int flag = 0;

    int count = 0;

    /**
     * 构造函数
     *
     * @param groups   组元素列表
     * @param children 子元素列表
     * @param context
     */
    public ShopcartAdapter(List<StoreInfo> groups, Map<String, List<GoodsInfo>> children, Context context) {
        this.groups = groups;
        this.children = children;
        this.context = context;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = groups.get(groupPosition).getShopId();
        return children.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<GoodsInfo> childs = children.get(groups.get(groupPosition).getShopId());
        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final GroupViewHolder gholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shopcart_group, null);
            gholder = new GroupViewHolder(convertView);
            convertView.setTag(gholder);
        } else {
            gholder = (GroupViewHolder) convertView.getTag();
        }
        final StoreInfo group = (StoreInfo) getGroup(groupPosition);

        gholder.tvSourceName.setText(group.getName());
        gholder.determineChekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());// 暴露组选接口
            }
        });
        gholder.determineChekbox.setChecked(group.isChoosed());

        gholder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("删除");
            }
        });
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {

        final ChildViewHolder cholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shopcart_product, null);

            cholder = new ChildViewHolder(convertView);
            convertView.setTag(cholder);
        } else {
            cholder = (ChildViewHolder) convertView.getTag();
        }

        /*if (groups.get(groupPosition).isEdtor() == true) {
            cholder.llEdtor.setVisibility(View.VISIBLE);
            cholder.rlNoEdtor.setVisibility(View.GONE);
        } else {
            cholder.llEdtor.setVisibility(View.GONE);
            cholder.rlNoEdtor.setVisibility(View.VISIBLE);
        }*/
        final GoodsInfo goodsInfo = (GoodsInfo) getChild(groupPosition, childPosition);


        /*if (isLastChild && getChild(groupPosition, childPosition) != null) {
            cholder.stub.setVisibility(View.VISIBLE);
            //  TextView tv= (TextView) cholder.stub.findViewById(R.id.txtFooter);//这里用来动态显示店铺满99元包邮文字内容
        } else {
            cholder.stub.setVisibility(View.GONE);
        }*/
        if (goodsInfo != null) {


            cholder.tvCommName.setText(goodsInfo.getDesc());
            cholder.tvCommPices.setText("￥" + goodsInfo.getPrice() + "");
            cholder.tvCommNum.setText(goodsInfo.getCount() + "");
//            cholder.ivCommPic.setImageResource(goodsInfo.getGoodsImg());
//            cholder.tvCommSpec.setText("颜色：" + goodsInfo.getColor() + "," + "尺码：" + goodsInfo.getSize() + "瓶/斤");
            /*SpannableString spanString = new SpannableString("￥" + String.valueOf(goodsInfo.getDiscountPrice()));
            StrikethroughSpan span = new StrikethroughSpan();
            spanString.setSpan(span, 0, String.valueOf(goodsInfo.getDiscountPrice()).length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //避免无限次的appand
            if (cholder.tvDiscountPrice.getText().toString().length() > 0) {
                cholder.tvDiscountPrice.setText("");
            }
            cholder.tvDiscountPrice.append(spanString);*/
            cholder.ivComm.setChecked(goodsInfo.isChoosed());
            cholder.ivComm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsInfo.setChoosed(((CheckBox) v).isChecked());
                    cholder.ivComm.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());// 暴露子选接口
                }
            });
            /*cholder.btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doIncrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露增加接口
                }
            });
            cholder.btReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doDecrease(groupPosition, childPosition, cholder.etNum, cholder.checkBox.isChecked());// 暴露删减接口
                }
            });*/
            /********************方案一：弹出软键盘修改数量，应为又不知名的bug会使然键盘强行关闭***********************/
            /****在清单文件的activity下设置键盘：
             android:windowSoftInputMode="adjustUnspecified|stateHidden|adjustPan"
             android:configChanges="orientation|keyboardHidden"****/
//            cholder.etNum.addTextChangedListener(new GoodsNumWatcher(goodsInfo));//监听文本输入框的文字变化，并且刷新数据
            notifyDataSetChanged();
            /********************方案一***************************************************************************/
            /********************方案二：让软键盘不能弹出，文本框不可编辑弹出dialog修改***********************/
//            cholder.etNum.setOnFocusChangeListener(new android.view.View.
//                    OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {//监听焦点的变化
//                    if (hasFocus) {//获取到焦点也就是文本框被点击修改了
//                        // 1，先强制键盘不弹出
//                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
//                        // 2.显示弹出dialog进行修改
//                        showDialog(goodsInfo,cholder.etNum);
            //3.清除焦点防止不断弹出dialog和软键盘
//                        cholder.etNum.clearFocus();
            // 4. 数据刷型
//                        ShopcartAdapter.this.notifyDataSetChanged();
//                    }
//                }
//            });
            /********************方案二***********************/
            //删除 购物车
            /*cholder.tvGoodsDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alert = new AlertDialog.Builder(context).create();
                    alert.setTitle("操作提示");
                    alert.setMessage("您确定要将这些商品从购物车中移除吗？");
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
                                    modifyCountInterface.childDelete(groupPosition, childPosition);

                                }
                            });
                    alert.show();

                }
            });*/

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;

    }

    private SlidingButtonView mMenu = null;

    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

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
        Log.i("asd", "mMenuΪnull");
        return false;
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param groupPosition
         * @param childPosition
         */
        void childDelete(int groupPosition, int childPosition);
    }


    /**
     * 组元素绑定器
     */
    static class GroupViewHolder {
        @Bind(R.id.determine_chekbox)
        CheckBox determineChekbox;
        @Bind(R.id.tv_source_name)
        TextView tvSourceName;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 子元素绑定器
     */
    class ChildViewHolder {
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

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
            ((SlidingButtonView) view).setSlidingButtonListener(ShopcartAdapter.this);
        }

    }

    /**
     * 购物车的数量修改编辑框的内容监听
     */
    class GoodsNumWatcher implements TextWatcher {
        GoodsInfo goodsInfo;

        public GoodsNumWatcher(GoodsInfo goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString())) {//当输入的数字不为空时，更新数字
                goodsInfo.setCount(Integer.valueOf(s.toString().trim()));
            }
        }

    }

    /**
     * 显示修改购物车商品数量的dialog
     *
     * @param goodinfo item的商品信息实体
     * @param edittext 购物车item的数量文本框
     */
    private void showDialog(final GoodsInfo goodinfo, final EditText edittext) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        View alertDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_change_num, null, false);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setView(alertDialogView);
        count = goodinfo.getCount();
        final EditText editText = (EditText) alertDialogView.findViewById(R.id.et_num);
        editText.setText("" + goodinfo.getCount());//设置dialog的数量初始值
        //自动弹出软键盘
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        final Button btadd = (Button) alertDialogView.findViewById(R.id.bt_add);
        final Button btreduce = (Button) alertDialogView.findViewById(R.id.bt_reduce);
        final TextView cancle = (TextView) alertDialogView.findViewById(R.id.tv_cancle);
        final TextView sure = (TextView) alertDialogView.findViewById(R.id.tv_sure);
        cancle.setOnClickListener(new View.OnClickListener() { //取消按钮
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {//确定按钮
            @Override
            public void onClick(View v) {
                goodinfo.setCount(count);//重新设置数量
                edittext.setText(count + "");//购物车界面的文本框显示同步
                alertDialog.dismiss();
            }
        });
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;   //点一下量加1
                editText.setText("" + count);//动态显示dialog的文本框的数据

            }
        });
        btreduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {//数量大雨1时操作
                    count--; //点一下减1
                    editText.setText("" + count);
                }
            }
        });
        alertDialog.show();
    }
}
