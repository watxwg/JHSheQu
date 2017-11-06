package com.jhcms.shequ.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/10.
 */
public class ChangeNameActivity extends SwipeBaseActivity {

    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.title_tv)
    TextView mTitleTv;
    @Bind(R.id.name_et)
    EditText mNameEt;
    @Bind(R.id.commit_btn)
    Button mCommitBtn;



    @Override
    protected void initData() {
        mTitleTv.setText("修改昵称");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_name);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_iv, R.id.commit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.commit_btn:

                break;
        }
    }
}
