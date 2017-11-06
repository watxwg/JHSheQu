package com.jhcms.shequ.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jhcms.common.model.Response;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenameActivity extends SwipeBaseActivity implements OnRequestSuccessCallback {

    @Bind(R.id.userName_ed)
    EditText mEdUsername;
    @Bind(R.id.submit_bt)
    TextView mSubmit;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void initData() {
        setToolBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        tvTitle.setText("修改昵称");
        Intent intent = getIntent();
        if (intent != null) {
            mEdUsername.setText(intent.getStringExtra("nickname"));
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_rename);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_bt:
                updatename();
                break;
        }
    }

    private void updatename() {
        if (TextUtils.isEmpty(mEdUsername.getText())) {
            ToastUtil.show("昵称不能为空！");
            return;
        }
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("nickname", mEdUsername.getText().toString());
            String str = jsonobject.toString();
            HttpUtils.postUrl(this, Api.SHEQU_UPDATENAME, str, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response response = gson.fromJson(Json, Response.class);
            switch (url) {
                case Api.SHEQU_UPDATENAME:
                    if (response.message != null)
                        ToastUtil.show(response.message);
                    RenameActivity.this.finish();
                    break;
            }

        } catch (Exception e) {

        }

    }

    @Override
    public void onBeforeAnimate() {

    }

    @Override
    public void onErrorAnimate() {

    }
}
