package com.jhcms.tuangou.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jhcms.common.model.Response_Mine;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.utils.StatusBarUtil;
import com.jhcms.common.utils.ToastUtil;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.RoundImageView;
import com.jhcms.shequ.activity.PersonalActivity;
import com.jhcms.shequ.model.HawkApi;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.UserMsgActivity;
import com.jhcms.waimaiV3.activity.WaimaiBalanceActivity;
import com.jhcms.waimaiV3.activity.WebViewActivity;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyujie
 * Date 2017/7/13.
 * TODO 团购我的界面
 */

public class Group_MineFragment extends WaiMai_BaseFragment implements OnRequestSuccessCallback {
    @Bind(R.id.mMineHead)
    RoundImageView mMineHead;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_user)
    LinearLayout llUser;
    @Bind(R.id.ll_mine_collection)
    LinearLayout llMineCollection;
    @Bind(R.id.ll_mine_balance)
    LinearLayout llMineBalance;
    @Bind(R.id.ll_invite_message)
    LinearLayout llInviteMessage;
    @Bind(R.id.tv_login_out)
    TextView tvLoginOut;
    @Bind(R.id.username_tv)
    TextView usernameTv;
    @Bind(R.id.UserPhone)
    TextView UserPhone;
    Response_Mine mDatamodel;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_mine, null);
        ButterKnife.bind(this, view);
        tvTitle.setText("我的");
        return view;
    }


    @Override
    protected void initData() {
        StatusBarUtil.immersive(getActivity());
        StatusBarUtil.setPaddingSmart(getActivity(), toolbar);
        if (TextUtils.isEmpty(Api.TOKEN)) {
            LoginOut();
        } else {
            LoginView();
        }
        RequestData();
    }

    private void RequestData() {
        if(!TextUtils.isEmpty(Api.TOKEN)) {
            HttpUtils.postUrl(getContext(), Api.SHEQU_USERINFO, null, false, this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            RequestData();
        }
    }

    /**
     * 绑定数据
     *
     * @param mDatamodel
     */
    private void BindViewData(Response_Mine mDatamodel) {
        LoginView();
        Glide.with(getContext()).load(Api.IMAGE_URL + mDatamodel.getData().getFace()).into(mMineHead);
        usernameTv.setText(mDatamodel.getData().getNickname());
        UserPhone.setText(mDatamodel.getData().getMobile());
    }

    @OnClick({R.id.ll_mine_collection, R.id.ll_mine_balance, R.id.ll_invite_message, R.id.tv_login_out, R.id.ll_user})
    public void onClick(View view) {

        if (Api.TOKEN == null || Api.TOKEN.equals("")) {
            Utils.GoLogin(getActivity());
        } else {
            Intent i = new Intent();
            switch (view.getId()) {
                case R.id.tv_login_out:
                    RequestLoginOut();
                    break;
                case R.id.ll_user:
                    i.setClass(getContext(), PersonalActivity.class);
                    startActivity(i);
                    break;
                case R.id.ll_mine_balance:
                    i.setClass(getContext(), WaimaiBalanceActivity.class);
                    startActivity(i);
                    break;
                case R.id.ll_mine_collection:
                    i.setClass(getContext(), WebViewActivity.class);
                    i.putExtra("WEB_URL", mDatamodel.getData().getCollect_tuan_url());
                    startActivity(i);
                    break;
                case R.id.ll_invite_message:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserMsgActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void RequestLoginOut() {
        JSONObject object = new JSONObject();
        try {
            object.put("register_id", Api.REGISTRATION_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.postUrl(getActivity(), Api.LOGIN_OUT, object.toString(), false, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(Api.TOKEN)) {
            RequestData();
        } else {
            LoginOut();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void LoginView() {
        tvLoginOut.setVisibility(View.VISIBLE);
        UserPhone.setVisibility(View.VISIBLE);

    }

    public void LoginOut() {
        usernameTv.setText("未登录");
        tvLoginOut.setVisibility(View.GONE);
        UserPhone.setVisibility(View.GONE);
        mMineHead.setImageResource(R.mipmap.mine_head);
    }

    @Override
    public void onSuccess(String url, String Json) {
        DismissDialog();
        Gson gson = new Gson();
        switch (url) {
            case Api.SHEQU_USERINFO:
                mDatamodel = gson.fromJson(Json, Response_Mine.class);
                if (mDatamodel.error.equals("0")) {
                    BindViewData(mDatamodel);
                } else {
                    if (mDatamodel.error.equals("101")) {
                        Api.TOKEN = null;
                        Utils.GoLogin(getActivity());
                    }
                    ToastUtil.show(mDatamodel.message);
                }
                break;
            case Api.LOGIN_OUT:
                if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                    Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                }
                Api.TOKEN = null;
                LoginOut();
                break;
        }

    }

    @Override
    public void onBeforeAnimate() {
        ShowLoadingDialog();
    }

    @Override
    public void onErrorAnimate() {
        DismissDialog();
    }
}
