package com.jhcms.mall.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.jhcms.common.utils.Api;
import com.jhcms.common.utils.HttpUtils;
import com.jhcms.common.utils.OnRequestSuccess;
import com.jhcms.common.utils.Utils;
import com.jhcms.mall.activity.MallProductDetailActivity;
import com.jhcms.mall.activity.MallShopDetailsActivity;
import com.jhcms.mall.activity.MallShopProduceListActivity;
import com.jhcms.mall.adapter.AdAdapter;
import com.jhcms.mall.adapter.HomeCateAdapter;
import com.jhcms.mall.adapter.HomeListAdapter;
import com.jhcms.mall.adapter.HomeShopAdapter;
import com.jhcms.mall.decoration.HomeAdDection;
import com.jhcms.mall.decoration.HomeListDection;
import com.jhcms.mall.model.BaseResponse;
import com.jhcms.mall.model.CateModel;
import com.jhcms.mall.model.HomeListModel;
import com.jhcms.mall.model.ImageInfoModel;
import com.jhcms.mall.model.ProductItemModel;
import com.jhcms.mall.model.ShopItemModel;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.fragment.WaiMai_BaseFragment;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/11.
 */
public class HomeFragment extends WaiMai_BaseFragment {
    private static final String TAG = "HomeFragment";

    @Bind(R.id.rv_home_list)
    RecyclerView rvHomeList;
    @Bind(R.id.refreshLayout)
    SpringView refreshLayout;
    @Bind(R.id.iv_back_home)
    ImageView ivBackHome;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    private List<ProductItemModel> mProductList;
    /*-----------------------headerView------------------------------*/
    private View headerView;
    private ConvenientBanner banner;
    private RecyclerView rvIndexCate;
    private ImageView ivShopEvent;
    private ImageView ivAdvOnce;
    private ImageView ivAdvTwo;
    private ImageView ivAdvThree;
    private ImageView ivAdvFour;
    private LinearLayout llShop;
    private LinearLayout llproduct;
    private RecyclerView rvShop;
    private ArrayList<ImageInfoModel> bannerData;
    private ArrayList<ShopItemModel> mShopList;
    private ArrayList<CateModel> mCateList;
    private HomeShopAdapter shopAdapter;
    private HomeCateAdapter cateAdapter;
    private HomeListAdapter homeListAdapter;
    /**
     * 当前导航栏样式，0表示透明，1表示不透明
     */
    private int currentTitleState = 0;
    private RecyclerView rvAd;
    private List<ImageInfoModel> adImageData;
    private AdAdapter<ImageInfoModel> adAdapter;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mall_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        setToolBar(titleLayout);
        initeView();
        requestData();
    }

    private void requestData() {
        HttpUtils.postUrlWithBaseResponse(getActivity(), Api.MALL_HOME
                , null, true, new OnRequestSuccess<BaseResponse<HomeListModel>>() {
                    @Override
                    public void onSuccess(String url, BaseResponse<HomeListModel> data) {
                        initData(data);
                    }

                    @Override
                    public void onBeforeAnimate() {

                    }

                    @Override
                    public void onErrorAnimate() {

                    }
                });
    }

    /**
     * 初始化数据
     *
     * @param data 列表数据model
     */
    private void initData(BaseResponse<HomeListModel> data) {
        HomeListModel listModel = data.getData();
        llproduct.setTag(listModel.getMore_product_url());
        llShop.setTag(listModel.getMore_shop_url());
        //轮播图数据
        List<ImageInfoModel> bannerList = listModel.getBanner();
        bannerData.clear();
        bannerData.addAll(bannerList);
        banner.notifyDataSetChanged();
        //广告图
        List<ImageInfoModel> adv = listModel.adv;
        adImageData.clear();
        adImageData.addAll(adv);
        adAdapter.notifyDataSetChanged();

        mCateList.clear();
        mCateList.addAll(listModel.getIndex_cate());
        cateAdapter.notifyDataSetChanged();
        cateAdapter.setOnCateClickListener((cateModel, position) -> {
            try {
                int cateId = Integer.parseInt(cateModel.getCate_id());
                Intent intent = MallShopProduceListActivity.generateIntent(getContext(), cateId, cateModel.getTitle(), MallShopProduceListActivity.CATE_TYPE);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent = MallShopProduceListActivity.generateIntent(getContext(), -1, cateModel.getTitle(), MallShopProduceListActivity.CATE_TYPE);
                startActivity(intent);
            }
        });
        //商家数据
        mShopList.clear();
        mShopList.addAll(listModel.getShop_items());
        shopAdapter.notifyDataSetChanged();
        //商品数据
        mProductList.clear();
        mProductList.addAll(listModel.getProduct_items());
        homeListAdapter.notifyDataSetChanged();
        refreshLayout.onFinishFreshAndLoad();
    }

    /**
     * 初始化list的headerView
     */
    private void initHeaderView(View headerView) {
        banner = (ConvenientBanner) headerView.findViewById(R.id.banner);
        rvIndexCate = (RecyclerView) headerView.findViewById(R.id.rv_index_cate);
        headerView.findViewById(R.id.ll_shop).setOnClickListener(v -> startActivity(MallShopProduceListActivity.generateIntent(getContext(), null, null, MallShopProduceListActivity.SHOP_TYPE)));
        headerView.findViewById(R.id.ll_product).setOnClickListener(v -> startActivity(MallShopProduceListActivity.generateIntent(getContext(), null, null, MallShopProduceListActivity.PRODUCT_TYPE)));
        //广告图片
        rvAd = (RecyclerView) headerView.findViewById(R.id.rv_ad);
        adImageData = new ArrayList<>();
        HashMap<Integer, Integer> lineHeight = new HashMap<>();
        float line_1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
        float line_2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, getResources().getDisplayMetrics());
        float decorationHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        lineHeight.put(0, (int) line_1);
        lineHeight.put(1, (int) line_2);
        lineHeight.put(2, (int) line_2);
        adAdapter = new AdAdapter<>(getContext(), adImageData, lineHeight, AdAdapter.TYPE_1_2_2);
        adAdapter.setOnItemClickListener((imageInfoModel, position) -> {
            String link = imageInfoModel.getLink();
            Intent intent = Utils.getMallSwitchIntent(getContext(), link);
            if (intent != null) {
                startActivity(intent);
            }
        });
        rvAd.setAdapter(adAdapter);
        rvAd.setLayoutManager(adAdapter.getLayoutManager());
        rvAd.addItemDecoration(new HomeAdDection((int) decorationHeight));

        llShop = (LinearLayout) headerView.findViewById(R.id.ll_shop);
        llproduct = (LinearLayout) headerView.findViewById(R.id.ll_product);
        rvShop = (RecyclerView) headerView.findViewById(R.id.rv_shop);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mShopList = new ArrayList<ShopItemModel>();
        mCateList = new ArrayList<CateModel>();
        shopAdapter = new HomeShopAdapter(getActivity(), mShopList);
        cateAdapter = new HomeCateAdapter(getActivity(), mCateList);
        rvIndexCate.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvIndexCate.setAdapter(cateAdapter);
        rvShop.setLayoutManager(layoutManager);
        rvShop.setAdapter(shopAdapter);
        shopAdapter.setOnShopClickListener((shopItemModel, position) -> {
            try {
                Intent intent = MallShopDetailsActivity.generateIntent(getContext(), Integer.parseInt(shopItemModel.getShop_id()));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        onBanner();
    }


    private void initeView() {
        onRefresh();
        mProductList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        rvHomeList.setLayoutManager(gridLayoutManager);
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        HomeListDection homeListDection = new HomeListDection(space);
        rvHomeList.addItemDecoration(homeListDection);

        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.mall_home_list_item_header, rvHomeList, false);
        initHeaderView(headerView);

        homeListAdapter = new HomeListAdapter(getActivity(), mProductList, headerView);
        homeListAdapter.setOnItemClickListener((productItemModel, position) -> {
            try {
                int productId = Integer.parseInt(productItemModel.getProduct_id());
                startActivity(MallProductDetailActivity.generateIntent(getActivity(), productId));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.mall_程序出错), Toast.LENGTH_SHORT).show();
                return;
            }
        });
        rvHomeList.setAdapter(homeListAdapter);
        rvHomeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalY = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalY += dy;
                if (banner != null && banner.getHeight() > 0) {
                    int height = banner.getHeight();
                    float percent = (totalY * 1.0f) / height;
                    if (percent > 1) {
                        percent = 1;

                    } else if (percent < 0) {
                        percent = 0;
                    }
                    if (percent >= 0.6 && currentTitleState != 1) {
                        currentTitleState = 1;
                        switchTitleStyle(false);
                    } else if (percent < 0.6 && currentTitleState != 0) {
                        currentTitleState = 0;
                        switchTitleStyle(true);
                    }
                    titleLayout.getBackground().mutate().setAlpha((int) (255 * percent));
                }


            }
        });


        titleLayout.getBackground().mutate().setAlpha(0);
        switchTitleStyle(true);

    }

    /**
     * 切换顶部导航栏的样式
     *
     * @param isAlpha
     */
    private void switchTitleStyle(boolean isAlpha) {
        int drawableDimen = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics());
        Rect drawableBounds = new Rect(0, 0, drawableDimen, drawableDimen);
        if (isAlpha) {
            ivBackHome.setImageResource(R.mipmap.mall_back_home_default);
            Drawable drawable = getResources().getDrawable(R.mipmap.mall_search_white);
            drawable.setBounds(drawableBounds);
            tvSearch.setCompoundDrawables(drawable, null, null, null);
            tvSearch.setTextColor(Color.WHITE);
            tvSearch.setBackgroundResource(R.drawable.bg_mall_home_search2);
        } else {
            ivBackHome.setImageResource(R.mipmap.mall_back_home_current);
            Drawable drawable = getResources().getDrawable(R.mipmap.mall_search_gray);
            drawable.setBounds(drawableBounds);
            tvSearch.setCompoundDrawables(drawable, null, null, null);
            tvSearch.setTextColor(getResources().getColor(R.color.mall_gray));
            tvSearch.setBackgroundResource(R.drawable.bg_mall_home_search);
        }
    }


    /**
     * 初始化刷新加载控件
     */
    private void onRefresh() {
        refreshLayout.setGive(SpringView.Give.BOTH);
        refreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                requestData();

            }

            @Override
            public void onLoadmore() {
                refreshLayout.onFinishFreshAndLoad();
            }
        });
        refreshLayout.setHeader(new DefaultHeader(getContext()));
        refreshLayout.setFooter(new DefaultFooter(getContext()));
        refreshLayout.setType(SpringView.Type.FOLLOW);
    }

    // 轮播图
    private void onBanner() {
        bannerData = new ArrayList<ImageInfoModel>();
        banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.iv_back_home, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_home:
                getActivity().finish();

                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), MallShopProduceListActivity.class));
                break;
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
            Utils.LoadStrPicture(getActivity(), Api.IMAGE_URL + data.getThumb(), imageView);
        }

    }
}
