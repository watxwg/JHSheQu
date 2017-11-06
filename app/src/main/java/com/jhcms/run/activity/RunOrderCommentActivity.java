package com.jhcms.run.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.classic.common.MultipleStatusView;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.mode.Data_Run_Order_Comment;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.SwipeBaseActivity;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RunOrderCommentActivity extends SwipeBaseActivity {

    private static String COMMENT_ID = "COMMENTID";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.riv_eva_staff_head)
    RoundImageView rivEvaStaffHead;
    @Bind(R.id.tv_staff_name)
    TextView tvStaffName;
    @Bind(R.id.tv_staff_time)
    TextView tvStaffTime;
    @Bind(R.id.rb_eva_staff)
    RatingBar rbEvaStaff;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.content_view)
    LinearLayout contentView;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView statusview;
    private String comment_id;


    @Override
    protected void initData() {
        setToolBar(toolbar);
        comment_id = getIntent().getStringExtra(COMMENT_ID);
        tvTitle.setText(R.string.评价);
        toolbar.setNavigationOnClickListener(v -> finish());
        requestInfo(Api.PAOTUI_ORDER_COMMON_DETAIL, comment_id);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_run_order_comment);
        ButterKnife.bind(this);
    }

    private void requestInfo(String api, String comment_id) {
        JSONObject object = new JSONObject();
        try {
            object.put("comment_id", comment_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpUtils.postUrlWithBaseResponse(this, api, object.toString(), false, new OnRequestSuccess<BaseResponse<Data_Run_Order_Comment>>() {
            @Override
            public void onSuccess(String url, BaseResponse<Data_Run_Order_Comment> data) {
                super.onSuccess(url, data);
                Data_Run_Order_Comment orderComment = data.getData();
                Data_Run_Order_Comment.StaffBean staffBean = orderComment.getStaff();
                Glide.with(RunOrderCommentActivity.this)
                        .load(Api.IMAGE_URL + staffBean.getFace())
                        .into(rivEvaStaffHead);
                tvStaffName.setText(staffBean.getName());
                tvStaffTime.setText(String.format(getString(R.string._分钟), orderComment.getPei_time()) + "(" + String.format(getString(R.string._送达), orderComment.getPei_complete_time() + ")"));
                rbEvaStaff.setRating(Integer.parseInt(orderComment.getScore()));
                if (!TextUtils.isEmpty(orderComment.getContent())) {
                    tvComment.setText(orderComment.getContent());
                }
                statusview.showContent();
            }

            @Override
            public void onBeforeAnimate() {
                statusview.showLoading();
            }

            @Override
            public void onAfter() {

            }

            @Override
            public void onErrorAnimate() {
                statusview.showError();
            }
        });
    }
    public static void startActivity(Context context, String comment_id) {
        Intent intent = new Intent(context, RunOrderCommentActivity.class);
        intent.putExtra(COMMENT_ID, comment_id);
        context.startActivity(intent);
    }
}
