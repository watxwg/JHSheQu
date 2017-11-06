package com.jhcms.run.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.common.widget.PickerView;
import com.jhcms.mall.fragment.HomeFragment;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.run.activity.RunHelpBuyActivity;
import com.jhcms.run.activity.RunHelpDeloveryActivity;
import com.jhcms.run.activity.RunMainActivity;
import com.jhcms.run.adapter.HomeBuyAdapter;
import com.jhcms.run.adapter.HomeDeliveryAdapter;
import com.jhcms.run.decoration.CommonDecoration;
import com.jhcms.run.dialog.PickerDialog;
import com.jhcms.run.mode.CateInfoModel;
import com.jhcms.run.mode.HomeDataModel;
import com.jhcms.run.mode.ImageInfoModel;
import com.jhcms.shequ.activity.LoginActivity;
import com.jhcms.waimaiV3.MyApplication;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;
import com.uuzuche.lib_zxing.decoding.FinishListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by wangyujie
 * on 2017/9/28.15:00
 * TODO
 */

public class Run_HomeFragment extends WaiMai_BaseFragment {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.banner)
    ConvenientBanner banner;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.v_buy_index)
    View vBuyIndex;
    @Bind(R.id.tv_delivery)
    TextView tvDelivery;
    @Bind(R.id.v_delivery_index)
    View vDeliveryIndex;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.bt_buy)
    Button btBuy;
    @Bind(R.id.rv_buy)
    RecyclerView rvBuy;
    @Bind(R.id.nsv_buy)
    NestedScrollView nsvBuy;
    @Bind(R.id.rv_delivery)
    RecyclerView rvDelivery;
    @Bind(R.id.tv_weight)
    TextView tvWeight;
    @Bind(R.id.ll_weight)
    LinearLayout llWeight;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.ll_price)
    LinearLayout llPrice;
    @Bind(R.id.bt_next)
    Button btNext;
    @Bind(R.id.nsc_delivery)
    NestedScrollView nscDelivery;
    private List<CateInfoModel> cateDataBuy;
    private List<CateInfoModel> cateDataDelivery;
    private HomeBuyAdapter buyAdapter;
    private HomeDeliveryAdapter deliveryAdapter;
    private List<String> mPriceList;
    private List<String> mWeightList;
    private ArrayList<String> mXiaofeiList;
    private ArrayList<ImageInfoModel> bannerData;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_run_home, null);
        ButterKnife.bind(this, view);
        titleTv.setText(R.string.跑腿服务);
        backIv.setVisibility(View.INVISIBLE);
        cateDataBuy = new ArrayList<>();
        cateDataDelivery = new ArrayList<>();
        buyAdapter = new HomeBuyAdapter(getContext(), cateDataBuy);
        buyAdapter.setItemClickListener(((cateInfoModel, position) -> {
            if (TextUtils.isEmpty(Api.TOKEN)) {
                Intent intent2 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent2);
                return;
            }
            Intent intent = RunHelpBuyActivity.generateIntent(getContext(), cateInfoModel.getTitle(), cateInfoModel.getCate_id(), mXiaofeiList, false, null);
            startActivity(intent);
        }));
        deliveryAdapter = new HomeDeliveryAdapter(getContext(), cateDataDelivery);
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        rvBuy.addItemDecoration(new CommonDecoration(space, 2));
        rvBuy.setAdapter(buyAdapter);
        rvDelivery.setAdapter(deliveryAdapter);
        rvBuy.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvDelivery.setLayoutManager(new GridLayoutManager(getContext(), 4));
        onBanner();
        requestData();
        return view;
    }

    private void requestData() {
        HttpUtils.postUrlWithBaseResponse(getContext(), Api.PAOTUI_HOME_DATA, null, true, new OnRequestSuccess<BaseResponse<HomeDataModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<HomeDataModel> data) {
                super.onSuccess(url, data);
                try {
                    processData(data.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, R.string.mall_程序出错, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 处理数据
     *
     * @param data
     * @throws Exception
     */
    private void processData(HomeDataModel data) throws Exception {
        if (data.getMai() != null) {
            cateDataBuy.addAll(data.getMai());
            buyAdapter.notifyDataSetChanged();
        }
        if (data.getSong() != null) {
            cateDataDelivery.addAll(data.getSong());
            deliveryAdapter.notifyDataSetChanged();
        }
        if (data.getBanner() != null) {
            bannerData.addAll(data.getBanner());
            banner.notifyDataSetChanged();
        }
        mPriceList = new ArrayList<>();
        mPriceList.addAll(data.getPrice());
        mWeightList = data.getWeight();
        mXiaofeiList = data.getXiaofei();
        if (mXiaofeiList != null) {
            MyApplication.runXiaoFei.clear();
            MyApplication.runXiaoFei.addAll(mXiaofeiList);
        }
    }

    // 轮播图
    private void onBanner() {
        bannerData = new ArrayList<ImageInfoModel>();
        banner.setPages(new CBViewHolderCreator<Run_HomeFragment.LocalImageHolderView>() {
            @Override
            public Run_HomeFragment.LocalImageHolderView createHolder() {
                return new Run_HomeFragment.LocalImageHolderView();
            }
        }, bannerData)
                .startTurning(3000)
                .setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(position -> {
                });
    }

    private void showPickerDialog(String desc, @NonNull List<String> selectList, @NonNull TextView textView) {
        PickerDialog dialog = new PickerDialog();
        if (selectList == null) {
            return;
        }
        dialog.setData(selectList, "");
        dialog.setDesc(desc);
        dialog.setWidth(0.75f, getResources().getDisplayMetrics());
        dialog.setOnSelectListener((select, position) -> {
            textView.setText(select);
        });
        dialog.show(getFragmentManager(), "pickerDialog");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.back_iv, R.id.tv_buy, R.id.tv_delivery, R.id.bt_buy, R.id.ll_weight, R.id.ll_price, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                break;
            case R.id.tv_buy:
                switchContent(true);
                break;
            case R.id.tv_delivery:
                switchContent(false);
                break;
            case R.id.bt_buy:
                if (TextUtils.isEmpty(etSearch.getText().toString())) {
                    Toast.makeText(mContext, R.string.输入想买的商品, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = RunHelpBuyActivity.generateIntent(getContext(), etSearch.getText().toString(), "0", mXiaofeiList, false, null);
                startActivity(intent);
                break;
            case R.id.ll_weight:
                if (mWeightList == null || mWeightList.size() == 0) {
                    return;
                }
                showPickerDialog(getString(R.string.选择物品重量), mWeightList, tvWeight);
                break;
            case R.id.ll_price:
                if (mPriceList == null || mPriceList.size() == 0) {
                    return;
                }
                showPickerDialog(getString(R.string.选择物品价值), mPriceList, tvPrice);
                break;
            case R.id.bt_next:
                if (TextUtils.isEmpty(Api.TOKEN)) {
                    Intent intent3 = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent3);
                    return;
                }
                goDelivery();
                break;
        }
    }

    /**
     * 跳转到帮我送
     */
    private void goDelivery() {
        String weight = tvWeight.getText().toString();
        String price = tvPrice.getText().toString();
        CateInfoModel selectCate = deliveryAdapter.getSelectCate();
        if (TextUtils.isEmpty(weight)) {
            Toast.makeText(mContext, R.string.请选择物品重量, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(mContext, R.string.请选择物品价值, Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectCate == null) {
            Toast.makeText(mContext, R.string.请选择物品类型, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = RunHelpDeloveryActivity.generateIntent(getContext(), selectCate.getTitle(), weight, price, selectCate.getCate_id(), mXiaofeiList, false, null);
        startActivity(intent);

    }

    /**
     * 切换显示内容
     *
     * @param isBuy true：帮我买 false：帮我送
     */
    public void switchContent(boolean isBuy) {
        if (isBuy) {
            tvBuy.setTextColor(Color.parseColor(getString(R.string.mall_color_theme)));
            vBuyIndex.setVisibility(View.VISIBLE);
            tvDelivery.setTextColor(Color.parseColor(getString(R.string.mall_color_333333)));
            vDeliveryIndex.setVisibility(View.INVISIBLE);
            nsvBuy.setVisibility(View.VISIBLE);
            nscDelivery.setVisibility(View.GONE);
        } else {
            tvBuy.setTextColor(Color.parseColor(getString(R.string.mall_color_333333)));
            vBuyIndex.setVisibility(View.INVISIBLE);
            tvDelivery.setTextColor(Color.parseColor(getString(R.string.mall_color_theme)));
            vDeliveryIndex.setVisibility(View.VISIBLE);
            nsvBuy.setVisibility(View.GONE);
            nscDelivery.setVisibility(View.VISIBLE);
        }
    }

    public class LocalImageHolderView implements Holder<ImageInfoModel> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, ImageInfoModel data) {
            Utils.LoadStrPicture(getActivity(), data.getThumb(), imageView);
        }

    }
}
