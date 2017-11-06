package com.jhcms.waimaiV3.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.jhcms.common.utils.Utils;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.adapter.OrderCancelAdapter;
import com.orhanobut.logger.Logger;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyujie
 * Date 2017/5/26.
 * TODO
 */

public class OrderCancelDialog extends Dialog {
    private final Context context;
    private final LayoutInflater mInflater;
    @Bind(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @Bind(R.id.et_reason)
    EditText etReason;
    @Bind(R.id.tv_giveup)
    TextView tvGiveup;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.gv_reason)
    GridView gvReason;
    private String[] mVals = {"临时有事", "点错了点错了", "填错信息", "不想买了", "付不了款", "其他"};

    private OnClickListener listener;
    /**
     * 补充的理由
     */
    private String content;
    /**
     * 点击标签返回的内容
     */
    private String tagContent;
    private OrderCancelAdapter cancelAdapter;
    private List<String> valData;

    public interface OnClickListener {
        void clickListener(String content);
    }

    public OrderCancelDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ordercancel_layout);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = Utils.getScreenW(context) * 4 / 5;
        window.setAttributes(lp);
        initData();
        flowlayout.setVisibility(View.GONE);
    }

    private void initData() {
        valData = new ArrayList<>();
        for (int i = 0; i < mVals.length; i++) {
            valData.add(mVals[i]);
        }
        Logger.d("valData" + valData.size());
        cancelAdapter = new OrderCancelAdapter(context);
        gvReason.setAdapter(cancelAdapter);
        cancelAdapter.setData(valData);
        gvReason.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cancelAdapter.setSelectPostion(position);
            }
        });










        /*flowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.dialog_cancel_item,
                        flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });*/

        /**
         *点击标签时的回调
         **/
        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                tagContent = mVals[position];
                return true;
            }
        });
        /**
         * 选择多个标签时的回调。
         * */
        flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Logger.d("标签点击"+selectPosSet);
            }
        });
    }

    @OnClick({R.id.tv_giveup, R.id.tv_ok})
    public void onClick(View view) {
        if (listener != null) {
            switch (view.getId()) {
                case R.id.tv_giveup:
                    break;
                case R.id.tv_ok:
                    content = etReason.getText().toString();
                    listener.clickListener(content);
                    break;
            }
            dismiss();
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

}
