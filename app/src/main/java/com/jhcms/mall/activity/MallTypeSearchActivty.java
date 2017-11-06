package com.jhcms.mall.activity;

import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;

public class MallTypeSearchActivty extends BaseActivity {
    private ImageView IvBack;
    private ImageView mIvDelete;
    private  ImageView mIvSeach;
    private TextView mLvdelete;
    private PopupWindow searchpopwin;
    private  TextView mTvSort;
    private EditText mEdseach;

    @Override
    protected void initView() {
        setContentView(R.layout.mall_type_search_activty);
    }

    @Override
    protected void initData() {
        inintsearchpopwi();
    }
    private void inintsearchpopwi() {
        View view= LayoutInflater.from(MallTypeSearchActivty.this).inflate(R.layout.item_mallsearchactivity_mall_select,null);
        searchpopwin=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        searchpopwin.setContentView(view);
        searchpopwin.setOutsideTouchable(true);
        searchpopwin.setBackgroundDrawable(new BitmapDrawable());
        searchpopwin.setFocusable(false);// 获取焦点
        final TextView mTvGood= (TextView) view.findViewById(R.id.Good_tv);
        final TextView mTvshop= (TextView) view.findViewById(R.id.shop_tv);
        mTvSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchpopwin.isShowing())
                    searchpopwin.showAsDropDown(mTvSort);
            }
        });
        mTvGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvSort.setText("商品");
                if(searchpopwin.isShowing()){
                    searchpopwin.dismiss();
                }
            }
        });
        mTvshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvSort.setText("店铺");
                if(searchpopwin.isShowing()){
                    searchpopwin.dismiss();
                }
            }
        });
    }
    @Override
    protected void initActionBar() {
        mEdseach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    mIvSeach.setVisibility(View.VISIBLE);
                    mIvDelete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        IvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initFindViewById() {
//        mEdseach= (EditText) this.findViewById(R.id.search_edit);
//        mIvDelete= (ImageView) this.findViewById(R.id.iv_delete);
//        mIvSeach= (ImageView) this.findViewById(R.id.seach_iv);
//        IvBack= (ImageView) this.findViewById(R.id.back_iv);
//        mLvdelete= (TextView) this.findViewById(R.id.lv_delete);
//        mTvSort= (TextView) this.findViewById(R.id.Sort_tv);

    }

    @Override
    protected void initEvent() {
            inintSeachEvent();
    }
    private void inintSeachEvent() {
        mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdseach.getText().clear();
                mIvDelete.setVisibility(View.GONE);
                mIvSeach.setVisibility(View.GONE);
            }
        });
        mIvSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MallTypeSearchActivty.this,"搜索",Toast.LENGTH_LONG).show();
            }
        });
    }
}
