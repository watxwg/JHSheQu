package com.jhcms.tuangou.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.common.widget.BaseActivity;
import com.jhcms.waimaiV3.R;

public class TuanBuyOlderActivity extends BaseActivity {
    private View mTitleView;
    private ImageView mBackIv;
    private TextView mTvTitlteContent;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_tuan_buy_older);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initActionBar() {
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuanBuyOlderActivity.this.finish();
            }
        });
        mTvTitlteContent.setText("确认订单");
    }

    @Override
    protected void initFindViewById() {
        mTitleView=this.findViewById(R.id.title);
        mBackIv= (ImageView) mTitleView.findViewById(R.id.back_iv);
        mTvTitlteContent= (TextView) mTitleView.findViewById(R.id.title_tv);

    }

    @Override
    protected void initEvent() {

    }
}
