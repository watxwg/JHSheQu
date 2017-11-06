package com.jhcms.run.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.classic.common.MultipleStatusView;
import com.jhcms.common.model.TimeModel;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.mode.Data_Run_Order_Comment;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;
import com.jhcms.waimaiV3.dialog.PickerViewDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunOrderEvaluationActivity extends SwipeBaseActivity {
    @Bind(R.id.riv_eva_staff_head)
    RoundImageView rivEvaStaffHead;
    @Bind(R.id.tv_staff_name)
    TextView tvStaffName;
    @Bind(R.id.tv_staff_time)
    TextView tvStaffTime;
    @Bind(R.id.tv_pei_scoring)
    TextView tvPeiScoring;
    @Bind(R.id.rb_eva_staff)
    RatingBar rbEvaStaff;
    @Bind(R.id.et_eva_staff)
    EditText etEvaStaff;
    @Bind(R.id.submit_bt)
    TextView submitBt;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView statusview;
    private String orderId;
    private PickerViewDialog dialog;
    private String mPeiTime = "0";


    @Override
    protected void initData() {
        setToolBar(toolbar);
        orderId = getIntent().getStringExtra(ORDER_ID);
        tvTitle.setText(R.string.评价);
        toolbar.setNavigationOnClickListener(v -> finish());
        requestInfo(Api.PAOTUI_ORDER_COMMENT, orderId);


    }

    private void requestInfo(String api, String order_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("order_id", order_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(this, api, object.toString(), false, new OnRequestSuccess<BaseResponse<Data_Run_Order_Comment>>() {
            @Override
            public void onSuccess(String url, BaseResponse<Data_Run_Order_Comment> data) {
                super.onSuccess(url, data);
                Data_Run_Order_Comment orderComment = data.getData();
                Data_Run_Order_Comment.StaffBean staffBean = orderComment.getStaff();
                Glide.with(RunOrderEvaluationActivity.this)
                        .load(Api.IMAGE_URL + staffBean.getFace())
                        .into(rivEvaStaffHead);
                tvStaffName.setText(staffBean.getName());
                tvStaffTime.setText(String.format(getString(R.string._分钟), orderComment.getMinute()) + "(" + String.format(getString(R.string._送达), orderComment.getPei_complete_time() + ")"));
            }

            @Override
            public void onBeforeAnimate() {
                statusview.showLoading();
            }

            @Override
            public void onAfter() {
                statusview.showContent();
            }

            @Override
            public void onErrorAnimate() {
                statusview.showError();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_run_order_evaluation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_pei_scoring, R.id.submit_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pei_scoring:
                dialog = new PickerViewDialog(this);
                dialog.setmDatas(getTimeMode());
                dialog.show();
                dialog.setHideTime(true);
                dialog.setOnSelectTimeListener((time, selectId) -> {
                    mPeiTime = getTimeMode().get(selectId).getMinute();
                    tvPeiScoring.setText(String.format(getString(R.string._分钟), mPeiTime));
                });
                break;
            case R.id.submit_bt:
                if (rbEvaStaff.getNumStars() == 0) {
                    ToastUtil.show("赏点分吧!");
                    return;
                }/* else if (TextUtils.isEmpty(etEvaStaff.getText().toString().trim())) {
                    ToastUtil.show("写点评价吧");
                    return;
                }*/
                submit();
                break;
        }
    }

    private void submit() {
        JSONObject object = new JSONObject();
        try {
            object.put("order_id", orderId);
            object.put("score", rbEvaStaff.getNumStars());
            object.put("pei_time", mPeiTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(this, Api.PAOTUI_ORDER_COMMON_HANDLE, object.toString(), true, new OnRequestSuccess<BaseResponse<Data_Run_Order_Comment>>() {
            @Override
            public void onSuccess(String url, BaseResponse<Data_Run_Order_Comment> data) {
                super.onSuccess(url, data);
                ToastUtil.show(data.message);
                finish();
            }

            @Override
            public void onBeforeAnimate() {
            }

            @Override
            public void onAfter() {
            }

            @Override
            public void onErrorAnimate() {
            }
        });
    }

    public List<TimeModel> getTimeMode() {
        List<TimeModel> timeModels = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            TimeModel model = new TimeModel();
            model.setDate(5 * i + "");
            model.setMinute(10 * i + "");
            timeModels.add(model);
        }
        return timeModels;
    }

    public static void startActivity(Context context, String order_id) {
        Intent intent = new Intent(context, RunOrderEvaluationActivity.class);
        intent.putExtra(ORDER_ID, order_id);
        context.startActivity(intent);
    }
}
