package com.jhcms.mall.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;

public class MallPaySuccessActivity extends BaseActivity {
    private View mTitleview;
    private ImageView mIvBack;
    private TextView mTitleContent;
    private TextView mBackIndex;
    private  TextView mLookOrder;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_pay_success);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallPaySuccessActivity.this.finish();
            }
        });
        mTitleContent.setText("消息提示");
    }

    @Override
    protected void initFindViewById() {
        mTitleview=this.findViewById(R.id.title);
        mIvBack= (ImageView) mTitleview.findViewById(R.id.back_iv);
        mTitleContent= (TextView) mTitleview.findViewById(R.id.title_tv);
        mBackIndex= (TextView) this.findViewById(R.id.backIndex);
        mLookOrder= (TextView) this.findViewById(R.id.lookOrder);
    }

    @Override
    protected void initEvent() {
        inintBackIndexEvent();
        inintLookOrder();

    }

    private void inintLookOrder() {
        mLookOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MallPaySuccessActivity.this,mLookOrder.getText(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inintBackIndexEvent() {
        mBackIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MallPaySuccessActivity.this,"回到首页",Toast.LENGTH_LONG).show();
            }
        });
    }
}
