package com.jhcms.mall.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.activity.MallProductDetailActivity;
import com.jhcms.mall.activity.MallShopDetailsActivity;
import com.jhcms.mall.adapter.AdAdapter;
import com.jhcms.mall.adapter.CouponsListAdapter;
import com.jhcms.mall.adapter.HomeListAdapter;
import com.jhcms.mall.adapter.ShopHomeListAdapter;
import com.jhcms.mall.decoration.HomeListDection;
import com.jhcms.mall.model.AdModel;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CouponsInfoModel;
import com.jhcms.mall.model.ImageInfoModel;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.mall.model.ShopDetailModel;
import com.jhcms.waimaiV3.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 店铺首页界面
 * create an instance of this fragment.
 */
public class ShopHomeFragment extends Fragment {
    private static final String TAG = ShopHomeFragment.class.getSimpleName();
    private static final String SHOP_ID = "shop_id";
    private RecyclerView rvList;
    private ConvenientBanner banner;
    private RecyclerView rvCoupons;
    private ImageView ivAd2;
    private ImageView ivAd3;
    private ImageView ivAd4;
    private ImageView ivAd5;
    private List<ImageInfoModel> bannerData;
    private List<ProductItemModel> productData;
    private ShopHomeListAdapter homeListAdapter;
    private int shopId;
    private List<CouponsInfoModel> couponsData;
    private CouponsListAdapter couponsListAdapter;
    private RecyclerView rvAd;
    //图片广告数据
    private List<AdModel> adData;
    //图片广告的适配器
    private AdAdapter<AdModel> adAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            shopId = arguments.getInt(SHOP_ID, -1);
        }
    }

    public static ShopHomeFragment newInstance(int shopId) {
        ShopHomeFragment fragment = new ShopHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SHOP_ID, shopId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_home, container, false);
        rvList = (RecyclerView) view.findViewById(R.id.rv_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        rvList.setLayoutManager(gridLayoutManager);
        View header = inflater.inflate(R.layout.mall_list_item_shop_home_header, rvList, false);
        initHeaderView(header);
        productData = new ArrayList<>();
        homeListAdapter = new ShopHomeListAdapter(getActivity(), productData, header);
        homeListAdapter.setOnItemClickListener((productItemModel, postion) -> {
            try {
                Intent intent = MallProductDetailActivity.generateIntent(getContext(), Integer.parseInt(productItemModel.getProduct_id()));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        rvList.setAdapter(homeListAdapter);
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        HomeListDection homeListDection = new HomeListDection(space);
        rvList.addItemDecoration(homeListDection);
        requestData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void requestData() {
        String paramsData = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shop_id", shopId);
            paramsData = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.MALL_SHOP_DETAIL, paramsData, true, new OnRequestSuccess<BaseResponse<ShopDetailModel>>() {
            @Override
            public void onSuccess(String url, BaseResponse<ShopDetailModel> data) {
                super.onSuccess(url, data);
                try {

                    handleData(data.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 处理数据
     *
     * @param data
     */
    private void handleData(ShopDetailModel data) throws Exception {
        if (getActivity() instanceof MallShopDetailsActivity) {
            ((MallShopDetailsActivity) getActivity()).setShopDetailInfo(data.getDetail(), data.getShare_info());
            if (data.getCates() != null && data.getCates().size() >= 0) {

                ((MallShopDetailsActivity) getActivity()).setCategoryData(data.getCates());
                Log.e(TAG, "handleData: catesize=" + data.getCates().size());
            }
        }
        List<ImageInfoModel> banners = data.getBanners();
        if (banners != null && banners.size() > 0) {

            bannerData.clear();
            bannerData.addAll(banners);
            banner.notifyDataSetChanged();
        } else {
            banner.setVisibility(View.GONE);
        }
        List<AdModel> advs = data.getAdvs();
        if (advs != null && advs.size() > 0) {
            adData.clear();
            adData.addAll(advs);
            adAdapter.notifyDataSetChanged();
        } else {
            rvAd.setVisibility(View.GONE);
        }
        Log.e("=======", "handleData: " + banners.size());
        List<CouponsInfoModel> coupons = data.getCoupons();
        couponsData.clear();
        couponsData.addAll(coupons);
        couponsListAdapter.notifyDataSetChanged();
        List<ProductItemModel> tuijian = data.getTuijian();
        productData.clear();
        productData.addAll(tuijian);
        homeListAdapter.notifyDataSetChanged();

    }

    /**
     * 初始化头部
     *
     * @param header
     */
    private void initHeaderView(View header) {
        banner = (ConvenientBanner) header.findViewById(R.id.banner);
        rvCoupons = (RecyclerView) header.findViewById(R.id.rv_coupons);
        rvAd = (RecyclerView) header.findViewById(R.id.rv_ad);
        adData = new ArrayList<>();
        HashMap<Integer, Integer> lineHeight = new HashMap<>();
        Integer line_140dp = Integer.valueOf((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140, getResources().getDisplayMetrics()));
        Integer line_90dp = Integer.valueOf((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics()));
        lineHeight.put(0, line_140dp);
        lineHeight.put(1, line_90dp);
        adAdapter = new AdAdapter<>(getContext(), adData, lineHeight, AdAdapter.TYPE_3_1);
        GridLayoutManager layoutManager = adAdapter.getLayoutManager();
        rvAd.setAdapter(adAdapter);
        rvAd.setLayoutManager(layoutManager);
        adAdapter.setOnItemClickListener((adModel, position) -> {
            Toast.makeText(getContext(), adModel.getLink(), Toast.LENGTH_SHORT).show();
        });
//        ivAd2 = (ImageView) header.findViewById(R.id.iv_ad2);
//        ivAd3 = (ImageView) header.findViewById(R.id.iv_ad3);
//        ivAd4 = (ImageView) header.findViewById(R.id.iv_ad4);
//        ivAd5 = (ImageView) header.findViewById(R.id.iv_ad5);
        couponsData = new ArrayList<CouponsInfoModel>();
        couponsListAdapter = new CouponsListAdapter(getActivity(), couponsData, R.layout.mall_list_item_coupons);
        rvCoupons.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvCoupons.setAdapter(couponsListAdapter);
        onBanner();
    }

    // 轮播图
    private void onBanner() {
        bannerData = new ArrayList<ImageInfoModel>();
        banner.setPages(new CBViewHolderCreator<ShopHomeFragment.LocalImageHolderView>() {
            @Override
            public ShopHomeFragment.LocalImageHolderView createHolder() {
                return new ShopHomeFragment.LocalImageHolderView();
            }
        }, bannerData)
                .startTurning(3000)
                .setPageIndicator(new int[]{R.mipmap.icon_banner_normal, R.mipmap.icon_banner_choose})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(position -> {
                    String link = bannerData.get(position).getLink();
                    Intent intent = Utils.getMallSwitchIntent(getContext(), link);
                    if (intent != null) {
                        startActivity(intent);
                    }
                });
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
            Utils.LoadStrPicture(getActivity(), Api.IMAGE_URL + data.getPhoto(), imageView);
        }

    }


}
