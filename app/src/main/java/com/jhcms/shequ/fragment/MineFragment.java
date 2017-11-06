package com.jhcms.shequ.fragment;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jhcms.common.model.Response;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccessCallback;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.shequ.activity.BankListActivity;
import com.jhcms.shequ.activity.LoginActivity;
import com.jhcms.shequ.activity.PersonalActivity;
import com.jhcms.shequ.adapter.OptionAdapter;
import com.jhcms.shequ.model.HawkApi;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.CustomerBaseFragment;
import com.orhanobut.hawk.Hawk;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2017/3/22.
 */
public class MineFragment extends CustomerBaseFragment implements OnRequestSuccessCallback {
    @Bind(R.id.head_iv)
    ImageView mHeadIv;
    @Bind(R.id.username_tv)
    TextView mUsernameTv;
    @Bind(R.id.mobile_tv)
    TextView mMobileTv;
    @Bind(R.id.head_ll)
    LinearLayout mHeadLl;
    @Bind(R.id.money_tv)
    TextView mMoneyTv;
    @Bind(R.id.money_ll)
    LinearLayout mMoneyLl;
    @Bind(R.id.hongbao_tv)
    TextView mHongbaoTv;
    @Bind(R.id.hongbao_ll)
    LinearLayout mHongbaoLl;
    @Bind(R.id.point_tv)
    TextView mPointTv;
    @Bind(R.id.point_ll)
    LinearLayout mPointLl;
    @Bind(R.id.first_lv)
    ListViewForScrollView mFirstLv;
    @Bind(R.id.second_lv)
    ListViewForScrollView mSecondLv;
    @Bind(R.id.third_lv)
    ListViewForScrollView mThirdLv;
    @Bind(R.id.exit_tv)
    TextView mExitTv;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private boolean IsLoginFlag = false;
    String[] firstInfos = new String[]{"收货地址", "我的收藏", "我的圈子"};
    String[] secondInfos = new String[]{"积分商城", "优惠券", "会员卡"};
    String[] thirdInfos = new String[]{"邀请好友", "申请合作"};
    OptionAdapter firstAdapter, secondAdapter, thirdAdapter;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        setToolBar(toolbar);
        tvTitle.setText("我的");
        firstAdapter = new OptionAdapter(getActivity());
        firstAdapter.setInfos(firstInfos);
        mFirstLv.setAdapter(firstAdapter);
        secondAdapter = new OptionAdapter(getActivity());
        secondAdapter.setInfos(secondInfos);
        mSecondLv.setAdapter(secondAdapter);
        thirdAdapter = new OptionAdapter(getActivity());
        thirdAdapter.setInfos(thirdInfos);
        mThirdLv.setAdapter(thirdAdapter);

    }

    private void mMobileTvAndmExittvIsGone() {
        if (IsLoginFlag) {
            mMobileTv.setVisibility(View.VISIBLE);
            mExitTv.setVisibility(View.VISIBLE);
        } else {
            mExitTv.setVisibility(View.GONE);
            mMobileTv.setVisibility(View.GONE);
        }
    }

    private void RequestData() {
        if (!TextUtils.isEmpty(Api.TOKEN)) {
            IsLoginFlag = true;
        }
        mMobileTvAndmExittvIsGone();
        if (!IsLoginFlag) {
            return;
        }
        try {
            HttpUtils.postUrl(getActivity(), Api.SHEQU_USERINFO, null, true, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.head_ll, R.id.username_tv, R.id.money_ll, R.id.hongbao_ll, R.id.point_ll, R.id.exit_tv})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.head_ll:
                if (IsLoginFlag == true) {
                    intent.setClass(getActivity(), PersonalActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.username_tv:
                if (IsLoginFlag == false) {
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.money_ll:
                break;
            case R.id.hongbao_ll:
                break;
            case R.id.point_ll:
                break;
            case R.id.exit_tv:
                if (Hawk.contains(HawkApi.SHEQU_USERINFO_TOKET)) {
                    Hawk.remove(HawkApi.SHEQU_USERINFO_TOKET);
                }
                Api.TOKEN = "";
                IsLoginFlag = false;
                mMobileTvAndmExittvIsGone();
                bindViewData(null, IsLoginFlag);
                break;
        }
    }

    @OnItemClick(R.id.first_lv)
    public void firstItems(int i) {
//        Toast.makeText(getActivity(), "firstItems=" + i, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getContext(), BankListActivity.class);
        startActivity(intent);
    }

    @OnItemClick(R.id.second_lv)
    public void secondItems(int i) {
        Toast.makeText(getActivity(), "secondItems=" + i, Toast.LENGTH_SHORT).show();
    }

    @OnItemClick(R.id.third_lv)
    public void thirdItems(int i) {
        Toast.makeText(getActivity(), "thirdItems=" + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 对view 出事化绑定数据
     *
     * @param response
     */
    private void bindViewData(Response response, boolean IsLoginFlag) {
        if (IsLoginFlag) {
            if (response.data.nickname != null) {
                mUsernameTv.setText(response.data.nickname);
            }
            if (response.data.mobile != null) {
                mMobileTv.setText(response.data.mobile);
            }
            if (response.data.money != null) {
                mMoneyTv.setText(response.data.money);
            }
            if (response.data.hongbao_count != null) {
                mHongbaoTv.setText(response.data.hongbao_count);
            }
            if (response.data.jifen != null) {
                mPointTv.setText(response.data.jifen);
            }
            if (response.data.face != null) {
                Glide.with(getContext()).load(Api.IMAGE_URL + response.data.face).into(mHeadIv);
            }
        } else {
            mUsernameTv.setText("登录 | 注册");
            mMobileTv.setText("");
            mMoneyTv.setText("0");
            mHongbaoTv.setText("0");
            mPointTv.setText("0");
            Glide.with(getContext()).load(R.mipmap.icon_head).into(mHeadIv);
        }
    }

    @Override
    public void onSuccess(String url, String Json) {
        try {
            Gson gson = new Gson();
            Response response = gson.fromJson(Json, Response.class);
            switch (url) {
                case Api.SHEQU_USERINFO:
                    bindViewData(response, IsLoginFlag);
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

    @Override
    protected void lazyLoad() {
        RequestData();
    }
}