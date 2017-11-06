package com.jhcms.mall.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;

/**
 * 申请售后页面
 */
public class MallSalesAfterActivity extends BaseActivity {
    private View mHeadview;
    private ImageView mIvBack;
    private TextView mTitleContent;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mall_sales_after);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initActionBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MallSalesAfterActivity.this.finish();
            }
        });
        mTitleContent.setText("申请售后");
    }

    @Override
    protected void initFindViewById() {
        mHeadview=this.findViewById(R.id.title);
        mIvBack= (ImageView) mHeadview.findViewById(R.id.back_iv);
        mTitleContent= (TextView) mHeadview.findViewById(R.id.title_tv);

    }

    @Override
    protected void initEvent() {

    }
}
